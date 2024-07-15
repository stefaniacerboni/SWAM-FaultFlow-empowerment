export interface OutputSystemDto {
  uuid: string;
  name: string;
  manufacturer: string;
  model: string;
  topLevelComponent: string;
}

export interface MetaComponentDto {
  name: string;
  compositionPorts: CompositionPortDto[];
  errorModes: ErrorModeDto[];
  propagationPorts: PropagationPortDto[];
}

export interface PropagationPortDto {
  propagatedFailureMode: string;
  exogenousFaultMode: string;
  affectedComponent: string;
  routingProbability: number;
}

export interface CompositionPortDto {
  parent: string;
  child: string;
}

export interface ErrorModeDto {
  name: string;
  activationFunction: string;
  outgoingFailure: string;
  inputFaultModes: FaultModeDto[];
  timetofailurePDF: string;
}

export interface FaultModeDto {
  faultType: string;
  name: string;
  arisingPDF: string;
}
