# Use an official Clojure image that has the clj command.
FROM clojure:tools-deps

# Install Node.js and npm for the frontend build
RUN apt-get update && \
    apt-get install -y curl && \
    curl -sL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs

# Set the working directory inside the container
WORKDIR /app

# Copy dependency definition files first to leverage Docker layer caching
COPY deps.edn package.json package-lock.json shadow-cljs.edn ./

# Install npm dependencies
RUN npm install

# Download Clojure dependencies to a cache layer
RUN clj -P

# Copy the rest of the application source code
COPY . .

# Expose the ports for the backend API, frontend dev server, and shadow-cljs REPL
EXPOSE 3000 8000 9630

# Set a default command to open a shell, allowing for interactive use
CMD ["/bin/bash"]

