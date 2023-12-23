import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './filiere-etudes.reducer';

export const FiliereEtudes = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const filiereEtudesList = useAppSelector(state => state.filiereEtudes.entities);
  const loading = useAppSelector(state => state.filiereEtudes.loading);

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
      <h2 id="filiere-etudes-heading" data-cy="FiliereEtudesHeading">
        <Translate contentKey="appApp.filiereEtudes.home.title">Filiere Etudes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="appApp.filiereEtudes.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/filiere-etudes/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="appApp.filiereEtudes.home.createLabel">Create new Filiere Etudes</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {filiereEtudesList && filiereEtudesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="appApp.filiereEtudes.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('nomFiliere')}>
                  <Translate contentKey="appApp.filiereEtudes.nomFiliere">Nom Filiere</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nomFiliere')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="appApp.filiereEtudes.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th>
                  <Translate contentKey="appApp.filiereEtudes.coursRequis">Cours Requis</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="appApp.filiereEtudes.nomConseiller">Nom Conseiller</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {filiereEtudesList.map((filiereEtudes, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/filiere-etudes/${filiereEtudes.id}`} color="link" size="sm">
                      {filiereEtudes.id}
                    </Button>
                  </td>
                  <td>{filiereEtudes.nomFiliere}</td>
                  <td>{filiereEtudes.description}</td>
                  <td>
                    {filiereEtudes.coursRequis
                      ? filiereEtudes.coursRequis.map((val, j) => (
                          <span key={j}>
                            <Link to={`/cours/${val.id}`}>{val.id}</Link>
                            {j === filiereEtudes.coursRequis.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {filiereEtudes.nomConseiller ? (
                      <Link to={`/conseiller-orientation/${filiereEtudes.nomConseiller.id}`}>{filiereEtudes.nomConseiller.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/filiere-etudes/${filiereEtudes.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/filiere-etudes/${filiereEtudes.id}/edit`}
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
                        onClick={() => (window.location.href = `/filiere-etudes/${filiereEtudes.id}/delete`)}
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
              <Translate contentKey="appApp.filiereEtudes.home.notFound">No Filiere Etudes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FiliereEtudes;
