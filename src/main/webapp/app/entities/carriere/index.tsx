import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Carriere from './carriere';
import CarriereDetail from './carriere-detail';
import CarriereUpdate from './carriere-update';
import CarriereDeleteDialog from './carriere-delete-dialog';

const CarriereRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Carriere />} />
    <Route path="new" element={<CarriereUpdate />} />
    <Route path=":id">
      <Route index element={<CarriereDetail />} />
      <Route path="edit" element={<CarriereUpdate />} />
      <Route path="delete" element={<CarriereDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CarriereRoutes;
