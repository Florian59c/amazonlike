# build Angular
FROM node:20 as build

WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

# nginx
FROM nginx:alpine
COPY --from=build /app/dist/front/browser/ /usr/share/nginx/html