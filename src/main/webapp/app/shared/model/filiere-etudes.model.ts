import { IEtudiant } from 'app/shared/model/etudiant.model';
import { ICours } from 'app/shared/model/cours.model';
import { IConseillerOrientation } from 'app/shared/model/conseiller-orientation.model';

export interface IFiliereEtudes {
  id?: number;
  nomFiliere?: string | null;
  description?: string | null;
  etudiants?: IEtudiant[] | null;
  coursRequis?: ICours[] | null;
  nomConseiller?: IConseillerOrientation | null;
}

export const defaultValue: Readonly<IFiliereEtudes> = {};
