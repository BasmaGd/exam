import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './conseiller-orientation.reducer';

export const ConseillerOrientationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const conseillerOrientationEntity = useAppSelector(state => state.conseillerOrientation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="conseillerOrientationDetailsHeading">
          <Translate contentKey="appApp.conseillerOrientation.detail.title">ConseillerOrientation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{conseillerOrientationEntity.id}</dd>
          <dt>
            <span id="nomConseiller">
              <Translate contentKey="appApp.conseillerOrientation.nomConseiller">Nom Conseiller</Translate>
            </span>
          </dt>
          <dd>{conseillerOrientationEntity.nomConseiller}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="appApp.conseillerOrientation.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{conseillerOrientationEntity.prenom}</dd>
          <dt>
            <span id="domaineExpertise">
              <Translate contentKey="appApp.conseillerOrientation.domaineExpertise">Domaine Expertise</Translate>
            </span>
          </dt>
          <dd>{conseillerOrientationEntity.domaineExpertise}</dd>
          <dt>
            <span id="coordonnees">
              <Translate contentKey="appApp.conseillerOrientation.coordonnees">Coordonnees</Translate>
            </span>
          </dt>
          <dd>{conseillerOrientationEntity.coordonnees}</dd>
        </dl>
        <Button tag={Link} to="/conseiller-orientation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/conseiller-orientation/${conseillerOrientationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConseillerOrientationDetail;
