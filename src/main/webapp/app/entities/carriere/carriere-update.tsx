import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConseillerOrientation } from 'app/shared/model/conseiller-orientation.model';
import { getEntities as getConseillerOrientations } from 'app/entities/conseiller-orientation/conseiller-orientation.reducer';
import { ICarriere } from 'app/shared/model/carriere.model';
import { getEntity, updateEntity, createEntity, reset } from './carriere.reducer';

export const CarriereUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const conseillerOrientations = useAppSelector(state => state.conseillerOrientation.entities);
  const carriereEntity = useAppSelector(state => state.carriere.entity);
  const loading = useAppSelector(state => state.carriere.loading);
  const updating = useAppSelector(state => state.carriere.updating);
  const updateSuccess = useAppSelector(state => state.carriere.updateSuccess);

  const handleClose = () => {
    navigate('/carriere');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
      ...carriereEntity,
      ...values,
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
          ...carriereEntity,
          nomConseiller: carriereEntity?.nomConseiller?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="appApp.carriere.home.createOrEditLabel" data-cy="CarriereCreateUpdateHeading">
            <Translate contentKey="appApp.carriere.home.createOrEditLabel">Create or edit a Carriere</Translate>
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
                  id="carriere-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('appApp.carriere.nomCariere')}
                id="carriere-nomCariere"
                name="nomCariere"
                data-cy="nomCariere"
                type="text"
              />
              <ValidatedField
                label={translate('appApp.carriere.prerequisAcademiques')}
                id="carriere-prerequisAcademiques"
                name="prerequisAcademiques"
                data-cy="prerequisAcademiques"
                type="text"
              />
              <ValidatedField
                id="carriere-nomConseiller"
                name="nomConseiller"
                data-cy="nomConseiller"
                label={translate('appApp.carriere.nomConseiller')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/carriere" replace color="info">
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

export default CarriereUpdate;
