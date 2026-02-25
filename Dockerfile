## Multi-stage Dockerfile: build Spring Boot JAR then create runtime image with Google Chrome

# --- Build stage ---
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY src ./src
# Build the application JAR (skip tests for faster builds by default)
RUN mvn -B -DskipTests package

# --- Runtime stage ---
FROM eclipse-temurin:17-jre-jammy

# 1. Install dependencies
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    ca-certificates \
    apt-transport-https \
    --no-install-recommends

# 2. Add Google Chrome's official GPG Key and Repository
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/googlechrome-linux-keyring.gpg \
    && echo "deb [arch=amd64 signed-by=/usr/share/keyrings/googlechrome-linux-keyring.gpg] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list

# 3. Update and Install Chrome
RUN apt-get update && apt-get install -y \
    google-chrome-stable \
    --no-install-recommends \
    && rm -rf /var/lib/apt/lists/*


# Verify Chrome installation
RUN google-chrome --version

# Install dependencies for downloading and unzipping
RUN apt-get update && apt-get install -y curl unzip jq --no-install-recommends

# Download matching ChromeDriver using the new JSON API
RUN CHROME_MAJOR_VERSION=$(google-chrome --version | sed -E 's/.* ([0-9]+)\..*/\1/') && \
    echo "Fetching ChromeDriver for Chrome major version: $CHROME_MAJOR_VERSION" && \
    DRIVER_URL=$(curl -s "https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions-with-downloads.json" | \
    jq -r ".channels.Stable.downloads.chromedriver[] | select(.platform==\"linux64\") | .url") && \
    curl -sSL -o /tmp/chromedriver.zip "$DRIVER_URL" && \
    unzip /tmp/chromedriver.zip -d /tmp/ && \
    mv /tmp/chromedriver-linux64/chromedriver /usr/bin/chromedriver && \
    chmod +x /usr/bin/chromedriver && \
    rm -rf /tmp/chromedriver.zip /tmp/chromedriver-linux64

# # Download matching ChromeDriver
# RUN CHROME_VERSION=$(google-chrome --version | grep -oP '\d+\.\d+\.\d+') && \
#     echo "Chrome version: $CHROME_VERSION" && \
#     wget -q https://chromedriver.storage.googleapis.com/$CHROME_VERSION/chromedriver_linux64.zip && \
#     unzip chromedriver_linux64.zip && \
#     mv chromedriver /usr/bin/chromedriver && \
#     chown root:root /usr/bin/chromedriver && \
#     chmod +x /usr/bin/chromedriver && \
#     rm chromedriver_linux64.zip

# Verify ChromeDriver installation
RUN chromedriver --version

# Install system deps and Google Chrome
# RUN apt-get update && apt-get install -y --no-install-recommends google-chrome-stable && rm -rf /var/lib/apt/lists/* && CHROME_VERSION=$(google-chrome --version | grep -oP '\d+\.\d+\.\d+') && wget https://chromedriver.storage.googleapis.com/$CHROME_VERSION/chromedriver_linux64.zip && unzip chromedriver_linux64.zip -d /usr/local/bin/ && rm chromedriver_linux64.zip
# RUN apt-get update \
#   && apt-get install -y --no-install-recommends wget gnupg unzip ca-certificates fonts-liberation libappindicator3-1 libasound2 libatk-bridge2.0-0 libgtk-3-0 libnss3 libxss1 libx11-xcb1 libgbm1 \
#   && wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
#   && sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list' \
#   && apt-get update \
#   && apt-get install -y --no-install-recommends google-chrome-stable \
#   && rm -rf /var/lib/apt/lists/*

# Create non-root user
RUN useradd -ms /bin/bash appuser
USER appuser
WORKDIR /home/appuser

# Copy application JAR from build stage
COPY --from=build /workspace/target/*.jar app.jar

# Recommended: mount /dev/shm to avoid Chrome crashes
VOLUME ["/dev/shm"]

EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","/home/appuser/app.jar"]
