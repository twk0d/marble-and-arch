# Build stage
FROM node:22-alpine AS build
WORKDIR /app

# Copy package files
COPY package*.json ./
RUN npm install

# Copy source code
COPY . .

# Set default API base for docker environment
ARG NUXT_PUBLIC_API_BASE=http://backend:8081/api
ENV NUXT_PUBLIC_API_BASE=$NUXT_PUBLIC_API_BASE

# Build the application
RUN npm run build

# Runtime stage
FROM node:22-alpine
WORKDIR /app

COPY --from=build /app/.output ./.output
COPY --from=build /app/package*.json ./

EXPOSE 3000

ENV HOST=0.0.0.0
ENV PORT=3000
ENV NUXT_PUBLIC_API_BASE=http://backend:8081/api

ENTRYPOINT ["node", ".output/server/index.mjs"]
