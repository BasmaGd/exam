import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICarriere } from 'app/shared/model/carriere.model';
import { getEntities as getCarrieres } from 'app/entities/carriere/carriere.reducer';
import { IFiliereEtudes } from 'app/shared/model/filiere-etudes.model';
import { getEntities as getFiliereEtudes } from 'app/entities/filiere-etudes/filiere-etudes.reducer';
import { ICours } from 'app/shared/model/cours.model';
import { getEntities as getCours } from 'app/entities/cours/cours.reducer';
import { IEtudiant } from 'app/shared/model/etudiant.model';
import { getEntity, updateEntity, createEntity, reset } from './etudiant.reducer';

export const EtudiantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const carrieres = useAppSelector(state => state.carriere.entities);
  const filiereEtudes = useAppSelector(state => state.filiereEtudes.entities);
  const cours = useAppSelector(state => state.cours.entities);
  const etudiantEntity = useAppSelector(state => state.etudiant.entity);
  const loading = useAppSelector(state => state.etudiant.loading);
  const updating = useAppSelector(state => state.etudiant.updating);
  const updateSuccess = useAppSelector(state => state.etudiant.updateSuccess);

  const handleClose = () => {
    navigate('/etudiant');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCarrieres({}));
    dispatch(getFiliereEtudes({}));
    dispatch(getCours({}));
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
    if (values.progressionAcademique !== undefined && typeof values.progressionAcademique !== 'number') {
      values.progressionAcademique = Number(values.progressionAcademique);
    }

    const entity = {
      ...etudiantEntity,
      ...values,
      nomCariere: carrieres.find(it => it.id.toString() === values.nomCariere.toString()),
      nomFiliere: filiereEtudes.find(it => it.id.toString() === values.nomFiliere.toString()),
      nomCours: cours.find(it => it.id.toString() === values.nomCours.toString()),
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
          ...etudiantEntity,
          nomCariere: etudiantEntity?.nomCariere?.id,
          nomFiliere: etudiantEntity?.nomFiliere?.id,
          nomCours: etudiantEntity?.nomCours?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="appApp.etudiant.home.createOrEditLabel" data-cy="EtudiantCreateUpdateHeading">
            <Translate contentKey="appApp.etudiant.home.createOrEditLabel">Create or edit a Etudiant</Translate>
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
                  id="etudiant-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('appApp.etudiant.nom')} id="etudiant-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField label={translate('appApp.etudiant.prenom')} id="etudiant-prenom" name="prenom" data-cy="prenom" type="text" />
              <ValidatedField label={translate('appApp.etudiant.email')} id="etudiant-email" name="email" data-cy="email" type="text" />
              <ValidatedField
                label={translate('appApp.etudiant.choixDeCarriere')}
                id="etudiant-choixDeCarriere"
                name="choixDeCarriere"
                data-cy="choixDeCarriere"
                type="text"
              />
              <ValidatedField
                label={translate('appApp.etudiant.progressionAcademique')}
                id="etudiant-progressionAcademique"
                name="progressionAcademique"
                data-cy="progressionAcademique"
                type="text"
              />
              <ValidatedField
                id="etudiant-nomCariere"
                name="nomCariere"
                data-cy="nomCariere"
                label={translate('appApp.etudiant.nomCariere')}
                type="select"
              >
                <option value="" key="0" />
                {carrieres
                  ? carrieres.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="etudiant-nomFiliere"
                name="nomFiliere"
                data-cy="nomFiliere"
                label={translate('appApp.etudiant.nomFiliere')}
                type="select"
              >
                <option value="" key="0" />
                {filiereEtudes
                  ? filiereEtudes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="etudiant-nomCours"
                name="nomCours"
                data-cy="nomCours"
                label={translate('appApp.etudiant.nomCours')}
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/etudiant" replace color="info">
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

export default EtudiantUpdate;
