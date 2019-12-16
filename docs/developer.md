# Developer Guide

## Setup Application Locally

Clone the repository locally:

```sh
    git clone https://github.com/diptadas/flarn.git
```

### Build & Run Backend Application

1. Building Backend Application:

```sh
    cd backend
    mvn package
```

2. Run Backend Application:

```sh
    java -jar target/flarn-<version>.jar
```

This will start a development server on port `8085`, which is used by the Frontend application.

### Build & Run Frontend Application

1. Building Frontend Application:

```sh
    cd frontend
    npm install
```

2. Run Frontend Application:

```sh
    npm run serve
```

This will start a development server on port `8080`, which can accessed by visiting http://localhost:8080.

## Deploying Application

We deployed the frontend to [Netlify](https://www.netlify.com/) and the backend to [Heroku](https://www.heroku.com/).

**TODO:** Add netlify and heroku scripts.

### Deploy the frontend:

```sh
    cd frontend
    npm run build
```

This will produce static HTML and JavaScript files, that can be deployed to any webserver.

### Deploy the backend:

Heroku expects a Java application in the root folder. Use this `git` command to push the backend subdirectory:

```sh
    git subtree push --prefix backend heroku master
```

