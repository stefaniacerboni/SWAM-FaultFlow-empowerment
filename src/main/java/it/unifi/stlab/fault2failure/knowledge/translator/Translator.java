package it.unifi.stlab.fault2failure.knowledge.translator;

import java.util.List;

import it.unifi.stlab.fault2failure.knowledge.propagation.ErrorMode;
import it.unifi.stlab.fault2failure.knowledge.propagation.PropagationPort;
import it.unifi.stlab.fault2failure.operational.Failure;

public interface Translator {
	void translate(List<ErrorMode> errorModes, List<PropagationPort> propagationPorts);

}
