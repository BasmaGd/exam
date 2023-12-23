import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './carriere.reducer';

export const CarriereDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const carriereEntity = useAppSelector(state => state.carriere.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="carriereDetailsHeading">
          <Translate contentKey="appApp.carriere.detail.title">Carriere</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{carriereEntity.id}</dd>
          <dt>
            <span id="nomCariere">
              <Translate contentKey="appApp.carriere.nomCariere">Nom Cariere</Translate>
            </span>
          </dt>
          <dd>{carriereEntity.nomCariere}</dd>
          <dt>
            <span id="prerequisAcademiques">
              <Translate contentKey="appApp.carriere.prerequisAcademiques">Prerequis Academiques</Translate>
            </span>
          </dt>
          <dd>{carriereEntity.prerequisAcademiques}</dd>
          <dt>
            <Translate contentKey="appApp.carriere.nomConseiller">Nom Conseiller</Translate>
          </dt>
          <dd>{carriereEntity.nomConseiller ? carriereEntity.nomConseiller.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/carriere" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/carriere/${carriereEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CarriereDetail;
