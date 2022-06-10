import React, { useState, useEffect } from "react";
import { useSearchParams } from 'react-router-dom';
import Spinner from "react-bootstrap/Spinner";
import Alert from 'react-bootstrap/Alert';
import Image from "react-bootstrap/Image";

import logo from "../assets/avilaT.png";

import "./account-activation.css";
import ApiService from "../services/api-service";

export default function AccountActivation(props) {
  const [state, setState] = useState({ loading: true, error: false, message: '', user: '' });
  const [searchParams] = useSearchParams();

  const api = new ApiService(setState);

  useEffect(() => {
    const token = searchParams.get('token');
    if (token === null) setState(Object.assign({}, state, { error: true, message: 'El token no es valido' }))
    else api.activateAccount(token);
  }, []);

  return (
    <div className="activate-container">
      <div className="form">
        <Image className={`logo ${state.error ? '' : 'movement'}`} src={logo} style={{ width: "30vh" }} />
        <h2>Activación de cuenta</h2>
        <hr style={{ height: "4px" }} />
        {state.error && (
          <div className="px-5">
          <Alert variant="danger"><b>Error. </b>{state.message}</Alert>
        </div>
        )}
        {(state.loading && !state.error) ? (
          <div style={{ display: "flex", justifyContent: "center" }}>
            <Spinner animation="border" variant="light" size="sm" />
            <h6 style={{ marginLeft: "20px" }}>Activando cuenta...</h6>
          </div>
        ) : !state.error && (
          <h4>{state.user} ¡Tu cuenta se activo exitosamente!</h4>
        )}
      </div>
    </div>
  );
}
