import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Etudiant from './etudiant';
import Carriere from './carriere';
import FiliereEtudes from './filiere-etudes';
import Cours from './cours';
import ConseillerOrientation from './conseiller-orientation';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="etudiant/*" element={<Etudiant />} />
        <Route path="carriere/*" element={<Carriere />} />
        <Route path="filiere-etudes/*" element={<FiliereEtudes />} />
        <Route path="cours/*" element={<Cours />} />
        <Route path="conseiller-orientation/*" element={<ConseillerOrientation />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
