FROM node:18 AS builder
WORKDIR /app
RUN corepack enable
COPY package.json pnpm-lock.yaml .npmrc?* ./ 2>$null
COPY angular.json tsconfig.json tsconfig.app.json tsconfig.spec.json ./
COPY src ./src
RUN pnpm install
RUN pnpm ng build --configuration production

FROM nginx:1.27-alpine
COPY --from=builder /app/dist/frontend/browser /usr/share/nginx/html
EXPOSE 80
