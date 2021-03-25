package it.unifi.stlab.faultflow.model.utils;

import it.unifi.stlab.faultflow.dao.FaultModeDao;
import it.unifi.stlab.faultflow.model.knowledge.propagation.BooleanExpression;
import it.unifi.stlab.faultflow.model.knowledge.propagation.EndogenousFaultMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.ExogenousFaultMode;
import it.unifi.stlab.faultflow.model.knowledge.propagation.FaultMode;
import org.json.JSONObject;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

@Dependent
@Default
@Converter(autoApply = true)
public class BooleanExpressionConverter implements AttributeConverter<BooleanExpression, String> {

    @Override
    public String convertToDatabaseColumn(BooleanExpression booleanExpression) {
        JSONObject dbDataObject = new JSONObject();
        if (booleanExpression == null) {
            return "";
        } else {
            dbDataObject.append("expression", booleanExpression.toSimpleString());
            HashMap<String, String> inputFaults = new HashMap<>();
            for (FaultMode faultMode : booleanExpression.extractIncomingFaults()) {
                String keyValue = faultMode.getUuid().toString()+","+faultMode.getClass().getSimpleName();
                if(faultMode.getClass().getSimpleName().equals("EndogenousFaultMode"))
                    keyValue+=","+((EndogenousFaultMode)faultMode).getArisingPDFToString();
                inputFaults.put(faultMode.getName(), keyValue);
            }
            dbDataObject.append("inputFaults", inputFaults);
            return dbDataObject.toString();
        }
    }

    @Override
    public BooleanExpression convertToEntityAttribute(String dbData) {
        if ("{}".equals(dbData)) {
            return null;
        }
        BooleanExpression be;
        JSONObject dbDataObject = new JSONObject(dbData);
        String expression = dbDataObject.getJSONArray("expression").getString(0);
        JSONObject inputFaults = (JSONObject) dbDataObject.getJSONArray("inputFaults").get(0);
        HashMap<String, FaultMode> faultmodes = new HashMap<>();
        Iterator<String> fault = inputFaults.keys();
        while (fault.hasNext()) {
            String key = fault.next();
            String[] values = inputFaults.get(key).toString().split(",");
            String uuid = values[0];
            if(values[1].equals("EndogenousFaultMode")){
                EndogenousFaultMode endogenousFaultMode = new EndogenousFaultMode(key, values[2]);
                endogenousFaultMode.setUuid(UUID.fromString(uuid));
                faultmodes.put(key, endogenousFaultMode);
            }
            else{
                ExogenousFaultMode exogenousFaultMode = new ExogenousFaultMode(key);
                exogenousFaultMode.setUuid(UUID.fromString(uuid));
                faultmodes.put(key, exogenousFaultMode);
            }
        }
        be = BooleanExpression.config(expression, faultmodes);
        return be;
    }
}
