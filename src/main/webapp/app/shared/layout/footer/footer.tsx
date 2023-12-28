import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <p>
          <Translate contentKey="footer">Your footer</Translate>
        </p>
        <p>Copyright &copy; Basma ELGARDA et Anass BOUPOUCHI</p>
      </Col>
    </Row>
  </div>
);

export default Footer;
