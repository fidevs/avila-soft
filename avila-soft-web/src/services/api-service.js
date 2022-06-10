import Axios from 'axios';
import env from '../environment';

export default class ApiService {
  constructor(updateState) {
    this.server = Axios.create({ baseURL: env.API_URL, headers: { 'Content-Type': 'application/json' } });

    this.updateState = updateState;
  }

  activateAccount = token => {
    this.server({ method: 'PUT', url: '/public/account', params: { token } }).then(response => {
      this.updateState({ user: response.data, error: false, loading: false, message: '' });
    }).catch(error => {
      console.log({error});
      if (error.response) {
        // The request was made and the server responded with a status code
        // that falls out of the range of 2xx
        if (error.response.status === 409) {
          this.updateState({
            user: '',
            error: true,
            loading: false,
            message: `${error.response.data.code}: ${error.response.data.message}`
          });
        } else {
          this.updateState({
            user: '',
            error: true,
            loading: false,
            message: 'Ocurrio un error en el servidor'
          });
        }
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
      } else if (error.request) {
        // The request was made but no response was received
        // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
        // http.ClientRequest in node.js
        this.updateState({
          user: '',
          error: true,
          loading: false,
          message: 'No se recibió respuesta del servidor'
        });
      } else {
        // Something happened in setting up the request that triggered an Error
        this.updateState({
          user: '',
          error: true,
          loading: false,
          message: 'Verificar conexión de internet'
        });
      }
    })
  }
}
