import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICours } from 'app/shared/model/cours.model';
import { getEntities as getCours } from 'app/entities/cours/cours.reducer';
import { IConseillerOrientation } from 'app/shared/model/conseiller-orientation.model';
import { getEntities as getConseillerOrientations } from 'app/entities/conseiller-orientation/conseiller-orientation.reducer';
import { IFiliereEtudes } from 'app/shared/model/filiere-etudes.model';
import { getEntity, updateEntity, createEntity, reset } from './filiere-etudes.reducer';

export const FiliereEtudesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cours = useAppSelector(state => state.cours.entities);
  const conseillerOrientations = useAppSelector(state => state.conseillerOrientation.entities);
  const filiereEtudesEntity = useAppSelector(state => state.filiereEtudes.entity);
  const loading = useAppSelector(state => state.filiereEtudes.loading);
  const updating = useAppSelector(state => state.filiereEtudes.updating);
  const updateSuccess = useAppSelector(state => state.filiereEtudes.updateSuccess);

  const handleClose = () => {
    navigate('/filiere-etudes');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCours({}));
    dispatch(getConseillerOrientations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...filiereEtudesEntity,
      ...values,
      coursRequis: mapIdList(values.coursRequis),
      nomConseiller: conseillerOrientations.find(it => it.id.toString() === values.nomConseiller.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...filiereEtudesEntity,
          coursRequis: filiereEtudesEntity?.coursRequis?.map(e => e.id.toString()),
          nomConseiller: filiereEtudesEntity?.nomConseiller?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="appApp.filiereEtudes.home.createOrEditLabel" data-cy="FiliereEtudesCreateUpdateHeading">
            <Translate contentKey="appApp.filiereEtudes.home.createOrEditLabel">Create or edit a FiliereEtudes</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="filiere-etudes-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('appApp.filiereEtudes.nomFiliere')}
                id="filiere-etudes-nomFiliere"
                name="nomFiliere"
                data-cy="nomFiliere"
                type="text"
              />
              <ValidatedField
                label={translate('appApp.filiereEtudes.description')}
                id="filiere-etudes-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('appApp.filiereEtudes.coursRequis')}
                id="filiere-etudes-coursRequis"
                data-cy="coursRequis"
                type="select"
                multiple
                name="coursRequis"
              >
                <option value="" key="0" />
                {cours
                  ? cours.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="filiere-etudes-nomConseiller"
                name="nomConseiller"
                data-cy="nomConseiller"
                label={translate('appApp.filiereEtudes.nomConseiller')}
                type="select"
              >
                <option value="" key="0" />
                {conseillerOrientations
                  ? conseillerOrientations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/filiere-etudes" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default FiliereEtudesUpdate;
