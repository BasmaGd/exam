import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ConseillerOrientation from './conseiller-orientation';
import ConseillerOrientationDetail from './conseiller-orientation-detail';
import ConseillerOrientationUpdate from './conseiller-orientation-update';
import ConseillerOrientationDeleteDialog from './conseiller-orientation-delete-dialog';

const ConseillerOrientationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ConseillerOrientation />} />
    <Route path="new" element={<ConseillerOrientationUpdate />} />
    <Route path=":id">
      <Route index element={<ConseillerOrientationDetail />} />
      <Route path="edit" element={<ConseillerOrientationUpdate />} />
      <Route path="delete" element={<ConseillerOrientationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConseillerOrientationRoutes;
