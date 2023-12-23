import { ICarriere } from 'app/shared/model/carriere.model';
import { IFiliereEtudes } from 'app/shared/model/filiere-etudes.model';
import { ICours } from 'app/shared/model/cours.model';

export interface IEtudiant {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  email?: string | null;
  choixDeCarriere?: string | null;
  progressionAcademique?: number | null;
  nomCariere?: ICarriere | null;
  nomFiliere?: IFiliereEtudes | null;
  nomCours?: ICours | null;
}

export const defaultValue: Readonly<IEtudiant> = {};
