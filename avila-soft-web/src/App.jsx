import React from "react";
import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";

import logo from "./assets/avila_blue.png";
import spring from "./assets/spring.png";
import react from "./assets/react.png";
import ionic from "./assets/ionic.png";

import "./App.css";

const INFO = [
  { img: spring, link: 'https://spring.io/projects/spring-boot', title: 'Backend', tecnology: 'Spring Boot', description: 'Spring Boot facilita la creación de aplicaciones basadas en Spring independientes y de grado de producción que puede "simplemente ejecutar".' },
  { img: react, link: 'https://es.reactjs.org/', title: 'Página Web', tecnology: 'ReactJs', description: 'React te ayuda a crear sencillamente interfaces de usuario interactivas. Diseña vistas simples, y React se encargará de actualizar y renderizar.' },
  { img: ionic, link: 'https://ionicframework.com/', title: 'Aplicación Móvil', tecnology: 'Ionic', description: 'Un conjunto de herramientas móviles de código abierto para crear experiencias de aplicaciones web y nativas multiplataforma de alta calidad.' }
];

export default function App() {
  return (
    <div className="app-container">
      <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
          <Navbar.Brand href="#Hola_:D">
            <img
              alt="logo.png"
              src={logo}
              width="30"
              height="30"
              className="d-inline-block align-top"
            />{" "}
            AvilaSoft
          </Navbar.Brand>
          <Button
            href="https://fontawesome.com/icons/code-simple?s=solid"
            variant="outline-primary"
            size="sm"
          >
            <i class="fa-brands fa-android"></i> Descargar App
          </Button>
        </Container>
      </Navbar>
      <h4 className="px-5 my-3 text-center" style={{color: 'rgb(106, 163, 196)'}}>
        Este pequeño sistema fué creado con la intención de motivar e incentivar a los alumnos del CBTis#179 para que den su mayor esfuerzo durante su preparación, como lo hizo el profesor <b>José Guadalupe Ávila Lira</b> con cada uno de sus alumnos.
        <br />
        <span style={{fontSize: '15px'}}>Atte: Fide Omar Islas Duarte, un ex alumno del CBTis 179</span>
      </h4>
      <h2 className="text-white mx-auto text-center">
        ¿Qué tecnologias se utilizaron para desarrollar este sistema?
      </h2>

      <div className="info-container m-3">
        <Row xs={1} md={3} className="p-0 justify-content-around">
          {INFO.map((_, idx) => (
            <Col style={{maxWidth: '20vw'}}>
              <Card style={{backgroundColor: 'rgba(0, 0, 0, 0)'}}>
                <Card.Title><h1 className="text-center">{_.title}</h1></Card.Title>
                <Card.Img variant="top" src={_.img} alt="image.png" />
                <Card.Body>
                  <Card.Title className="mb-2"><h3>{_.tecnology}</h3></Card.Title>
                  <Card.Text className="text-center"><h5>{_.description}</h5></Card.Text>
                </Card.Body>
                <a href={_.link} rel="noreferrer" target="_blank" className="learn-more text-center text-white text-bold">Saber más...</a>
              </Card>
            </Col>
          ))}
        </Row>
      </div>
    </div>
  );
}
