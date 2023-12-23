import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConseillerOrientation } from 'app/shared/model/conseiller-orientation.model';
import { getEntity, updateEntity, createEntity, reset } from './conseiller-orientation.reducer';

export const ConseillerOrientationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const conseillerOrientationEntity = useAppSelector(state => state.conseillerOrientation.entity);
  const loading = useAppSelector(state => state.conseillerOrientation.loading);
  const updating = useAppSelector(state => state.conseillerOrientation.updating);
  const updateSuccess = useAppSelector(state => state.conseillerOrientation.updateSuccess);

  const handleClose = () => {
    navigate('/conseiller-orientation');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
      ...conseillerOrientationEntity,
      ...values,
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
          ...conseillerOrientationEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="appApp.conseillerOrientation.home.createOrEditLabel" data-cy="ConseillerOrientationCreateUpdateHeading">
            <Translate contentKey="appApp.conseillerOrientation.home.createOrEditLabel">Create or edit a ConseillerOrientation</Translate>
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
                  id="conseiller-orientation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('appApp.conseillerOrientation.nomConseiller')}
                id="conseiller-orientation-nomConseiller"
                name="nomConseiller"
                data-cy="nomConseiller"
                type="text"
              />
              <ValidatedField
                label={translate('appApp.conseillerOrientation.prenom')}
                id="conseiller-orientation-prenom"
                name="prenom"
                data-cy="prenom"
                type="text"
              />
              <ValidatedField
                label={translate('appApp.conseillerOrientation.domaineExpertise')}
                id="conseiller-orientation-domaineExpertise"
                name="domaineExpertise"
                data-cy="domaineExpertise"
                type="text"
              />
              <ValidatedField
                label={translate('appApp.conseillerOrientation.coordonnees')}
                id="conseiller-orientation-coordonnees"
                name="coordonnees"
                data-cy="coordonnees"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/conseiller-orientation" replace color="info">
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

export default ConseillerOrientationUpdate;
