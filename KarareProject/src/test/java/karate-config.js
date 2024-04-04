function fn() {

  var env = karate.env; // get system property 'karate.env'

  karate.log('karate.env system property was:', env);

  if (!env) {
    env = 'dev';
  }

  var config = {

    env: env,
    baseUrl: 'https://restful-booker.herokuapp.com',
    login_credentials: {
        username: 'admin',
        password: 'password123'
    }

  }
  if (env == 'dev') {
    // customize
    // e.g. config.foo = 'bar';
  } else if (env == 'e2e') {
    // customize
  }
  return config;
}
