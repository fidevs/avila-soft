const ENVIRONMENT = { // TODO: Encrypt sensitive values
  development: {
    API_URL: 'http://localhost:8080',
    AUTH_CLIENT: '',
    AUTH_SECRET: '',
  },
  production: {
    API_URL: 'http://localhost:8080',
    AUTH_CLIENT: '',
    AUTH_SECRET: '',
  }
};

export default ENVIRONMENT[process.env.NODE_ENV]
