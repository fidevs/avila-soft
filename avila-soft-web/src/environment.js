const ENVIRONMENT = { // TODO: Encrypt sensitive values
  development: {
    API_URL: 'http://localhost:8080',
  },
  production: {
    API_URL: 'http://localhost:8080',
  }
};

export default ENVIRONMENT[process.env.NODE_ENV]
