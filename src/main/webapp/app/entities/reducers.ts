import etudiant from 'app/entities/etudiant/etudiant.reducer';
import carriere from 'app/entities/carriere/carriere.reducer';
import filiereEtudes from 'app/entities/filiere-etudes/filiere-etudes.reducer';
import cours from 'app/entities/cours/cours.reducer';
import conseillerOrientation from 'app/entities/conseiller-orientation/conseiller-orientation.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  etudiant,
  carriere,
  filiereEtudes,
  cours,
  conseillerOrientation,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
