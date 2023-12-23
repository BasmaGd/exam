import { ICarriere } from 'app/shared/model/carriere.model';
import { IFiliereEtudes } from 'app/shared/model/filiere-etudes.model';

export interface IConseillerOrientation {
  id?: number;
  nomConseiller?: string | null;
  prenom?: string | null;
  domaineExpertise?: string | null;
  coordonnees?: string | null;
  carrieres?: ICarriere[] | null;
  filiereEtudes?: IFiliereEtudes[] | null;
}

export const defaultValue: Readonly<IConseillerOrientation> = {};
