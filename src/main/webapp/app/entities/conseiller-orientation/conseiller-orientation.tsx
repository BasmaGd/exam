import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './conseiller-orientation.reducer';

export const ConseillerOrientation = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const conseillerOrientationList = useAppSelector(state => state.conseillerOrientation.entities);
  const loading = useAppSelector(state => state.conseillerOrientation.loading);

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
      <h2 id="conseiller-orientation-heading" data-cy="ConseillerOrientationHeading">
        <Translate contentKey="appApp.conseillerOrientation.home.title">Conseiller Orientations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="appApp.conseillerOrientation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/conseiller-orientation/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="appApp.conseillerOrientation.home.createLabel">Create new Conseiller Orientation</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {conseillerOrientationList && conseillerOrientationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="appApp.conseillerOrientation.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('nomConseiller')}>
                  <Translate contentKey="appApp.conseillerOrientation.nomConseiller">Nom Conseiller</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nomConseiller')} />
                </th>
                <th className="hand" onClick={sort('prenom')}>
                  <Translate contentKey="appApp.conseillerOrientation.prenom">Prenom</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('prenom')} />
                </th>
                <th className="hand" onClick={sort('domaineExpertise')}>
                  <Translate contentKey="appApp.conseillerOrientation.domaineExpertise">Domaine Expertise</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('domaineExpertise')} />
                </th>
                <th className="hand" onClick={sort('coordonnees')}>
                  <Translate contentKey="appApp.conseillerOrientation.coordonnees">Coordonnees</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('coordonnees')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {conseillerOrientationList.map((conseillerOrientation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/conseiller-orientation/${conseillerOrientation.id}`} color="link" size="sm">
                      {conseillerOrientation.id}
                    </Button>
                  </td>
                  <td>{conseillerOrientation.nomConseiller}</td>
                  <td>{conseillerOrientation.prenom}</td>
                  <td>{conseillerOrientation.domaineExpertise}</td>
                  <td>{conseillerOrientation.coordonnees}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/conseiller-orientation/${conseillerOrientation.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/conseiller-orientation/${conseillerOrientation.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/conseiller-orientation/${conseillerOrientation.id}/delete`)}
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
              <Translate contentKey="appApp.conseillerOrientation.home.notFound">No Conseiller Orientations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ConseillerOrientation;
