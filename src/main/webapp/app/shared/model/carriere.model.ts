import { IEtudiant } from 'app/shared/model/etudiant.model';
import { IConseillerOrientation } from 'app/shared/model/conseiller-orientation.model';

export interface ICarriere {
  id?: number;
  nomCariere?: string | null;
  prerequisAcademiques?: string | null;
  etudiants?: IEtudiant[] | null;
  nomConseiller?: IConseillerOrientation | null;
}

export const defaultValue: Readonly<ICarriere> = {};
