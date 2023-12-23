import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './etudiant.reducer';

export const Etudiant = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const etudiantList = useAppSelector(state => state.etudiant.entities);
  const loading = useAppSelector(state => state.etudiant.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="etudiant-heading" data-cy="EtudiantHeading">
        <Translate contentKey="appApp.etudiant.home.title">Etudiants</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="appApp.etudiant.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/etudiant/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="appApp.etudiant.home.createLabel">Create new Etudiant</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {etudiantList && etudiantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="appApp.etudiant.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('nom')}>
                  <Translate contentKey="appApp.etudiant.nom">Nom</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('nom')} />
                </th>
                <th className="hand" onClick={sort('prenom')}>
                  <Translate contentKey="appApp.etudiant.prenom">Prenom</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('prenom')} />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="appApp.etudiant.email">Email</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                </th>
                <th className="hand" onClick={sort('choixDeCarriere')}>
                  <Translate contentKey="appApp.etudiant.choixDeCarriere">Choix De Carriere</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('choixDeCarriere')} />
                </th>
                <th className="hand" onClick={sort('progressionAcademique')}>
                  <Translate contentKey="appApp.etudiant.progressionAcademique">Progression Academique</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('progressionAcademique')} />
                </th>
                <th>
                  <Translate contentKey="appApp.etudiant.nomCariere">Nom Cariere</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="appApp.etudiant.nomFiliere">Nom Filiere</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="appApp.etudiant.nomCours">Nom Cours</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {etudiantList.map((etudiant, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/etudiant/${etudiant.id}`} color="link" size="sm">
                      {etudiant.id}
                    </Button>
                  </td>
                  <td>{etudiant.nom}</td>
                  <td>{etudiant.prenom}</td>
                  <td>{etudiant.email}</td>
                  <td>{etudiant.choixDeCarriere}</td>
                  <td>{etudiant.progressionAcademique}</td>
                  <td>{etudiant.nomCariere ? <Link to={`/carriere/${etudiant.nomCariere.id}`}>{etudiant.nomCariere.id}</Link> : ''}</td>
                  <td>
                    {etudiant.nomFiliere ? <Link to={`/filiere-etudes/${etudiant.nomFiliere.id}`}>{etudiant.nomFiliere.id}</Link> : ''}
                  </td>
                  <td>{etudiant.nomCours ? <Link to={`/cours/${etudiant.nomCours.id}`}>{etudiant.nomCours.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/etudiant/${etudiant.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/etudiant/${etudiant.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/etudiant/${etudiant.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="appApp.etudiant.home.notFound">No Etudiants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Etudiant;
