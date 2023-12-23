import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FiliereEtudes from './filiere-etudes';
import FiliereEtudesDetail from './filiere-etudes-detail';
import FiliereEtudesUpdate from './filiere-etudes-update';
import FiliereEtudesDeleteDialog from './filiere-etudes-delete-dialog';

const FiliereEtudesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FiliereEtudes />} />
    <Route path="new" element={<FiliereEtudesUpdate />} />
    <Route path=":id">
      <Route index element={<FiliereEtudesDetail />} />
      <Route path="edit" element={<FiliereEtudesUpdate />} />
      <Route path="delete" element={<FiliereEtudesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FiliereEtudesRoutes;
