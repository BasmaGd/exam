import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './filiere-etudes.reducer';

export const FiliereEtudesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const filiereEtudesEntity = useAppSelector(state => state.filiereEtudes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="filiereEtudesDetailsHeading">
          <Translate contentKey="appApp.filiereEtudes.detail.title">FiliereEtudes</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{filiereEtudesEntity.id}</dd>
          <dt>
            <span id="nomFiliere">
              <Translate contentKey="appApp.filiereEtudes.nomFiliere">Nom Filiere</Translate>
            </span>
          </dt>
          <dd>{filiereEtudesEntity.nomFiliere}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="appApp.filiereEtudes.description">Description</Translate>
            </span>
          </dt>
          <dd>{filiereEtudesEntity.description}</dd>
          <dt>
            <Translate contentKey="appApp.filiereEtudes.coursRequis">Cours Requis</Translate>
          </dt>
          <dd>
            {filiereEtudesEntity.coursRequis
              ? filiereEtudesEntity.coursRequis.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {filiereEtudesEntity.coursRequis && i === filiereEtudesEntity.coursRequis.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="appApp.filiereEtudes.nomConseiller">Nom Conseiller</Translate>
          </dt>
          <dd>{filiereEtudesEntity.nomConseiller ? filiereEtudesEntity.nomConseiller.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/filiere-etudes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/filiere-etudes/${filiereEtudesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FiliereEtudesDetail;
