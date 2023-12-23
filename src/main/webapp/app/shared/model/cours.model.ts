import { IEtudiant } from 'app/shared/model/etudiant.model';
import { IFiliereEtudes } from 'app/shared/model/filiere-etudes.model';

export interface ICours {
  id?: number;
  nomCours?: string | null;
  description?: string | null;
  etudiants?: IEtudiant[] | null;
  filiereEtudes?: IFiliereEtudes[] | null;
}

export const defaultValue: Readonly<ICours> = {};
