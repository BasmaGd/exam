import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/etudiant">
        <Translate contentKey="global.menu.entities.etudiant" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/carriere">
        <Translate contentKey="global.menu.entities.carriere" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/filiere-etudes">
        <Translate contentKey="global.menu.entities.filiereEtudes" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cours">
        <Translate contentKey="global.menu.entities.cours" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/conseiller-orientation">
        <Translate contentKey="global.menu.entities.conseillerOrientation" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
