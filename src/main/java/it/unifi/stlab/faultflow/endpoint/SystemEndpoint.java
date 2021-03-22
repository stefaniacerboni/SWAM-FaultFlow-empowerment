package it.unifi.stlab.faultflow.endpoint;

import it.unifi.stlab.faultflow.dto.inputsystemdto.InputSystemDto;
import it.unifi.stlab.faultflow.exporter.PetriNetExportMethod;
import it.unifi.stlab.faultflow.exporter.XPNExporter;
import it.unifi.stlab.faultflow.exporter.strategies.OrderByComponentToXPN;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.mapper.FaultTreeMapper;
import it.unifi.stlab.faultflow.mapper.SystemMapper;
import it.unifi.stlab.faultflow.translator.PetriNetTranslator;
import it.unifi.stlab.launcher.systembuilder.SimpleSystem02Builder;

import javax.enterprise.inject.Default;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

@Stateless
@Path("/system")
public class SystemEndpoint {
    @POST
    @Path("/xpn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetriNetXPN(InputSystemDto inputSystemDto,
                                   @QueryParam("method") @DefaultValue("fa") String method) {
        PetriNetTranslator pnt = new PetriNetTranslator();
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        File out = new File("PetriNet.xpn");
        try {
            pnt.translate(sys, PetriNetExportMethod.fromString(method));
            XPNExporter.export(out, new OrderByComponentToXPN(sys, pnt.getPetriNet(), pnt.getMarking()));
            return Response.ok(out).header("Content-Disposition", "attachment; filename=" + "PetriNet.xpn").build();
        } catch (FileNotFoundException fnf) {
            throw new NotFoundException("File not Found");
        } catch (JAXBException e) {
            throw new BadRequestException("There's been a problem with the conversion to XPN");
        } catch (Exception e) {
            throw new InternalServerErrorException("Unexpected Server Problem");
        }
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSystem(InputSystemDto inputSystemDto) {
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        return Response.ok(FaultTreeMapper.systemToOutputSystem(sys)).build();
    }

    @GET
    @Path("")
    public Response restTest() {
        return Response.ok().build();
    }

    @GET
    @Path("/db")
    public Response db() throws Exception {
        System s = SimpleSystem02Builder.getInstance().getSystem();
        systemDao.save(s);
        return Response.ok().build();
    }

    @GET
    @Path("/em")
    public Response em() throws Exception {
        HashMap<String, FaultMode> faultModes = new HashMap<>();
        EndogenousFaultMode enFM_A1 = new EndogenousFaultMode("A_Fault1");
        enFM_A1.setArisingPDF("dirac(3)");
        EndogenousFaultMode enFM_A3 = new EndogenousFaultMode("A_Fault3");
        enFM_A3.setArisingPDF("exp(10)");
        ExogenousFaultMode exFM_A2 = new ExogenousFaultMode("A_Fault2");
        faultModes.put(enFM_A1.getName(), enFM_A1);
        faultModes.put(enFM_A3.getName(), enFM_A3);
        faultModes.put(exFM_A2.getName(), exFM_A2);
        FailureMode fM_A1 = new FailureMode("A_Failure1");
        ErrorMode eM_A1 = new ErrorMode("A_ToFailure1");
        eM_A1.addInputFaultMode(enFM_A1, exFM_A2, enFM_A3);
        eM_A1.addOutputFailureMode(fM_A1);
        eM_A1.setEnablingCondition("A_Fault1 && (A_Fault2 || A_Fault3)", faultModes);
        eM_A1.setPDF("erlang(5,1)");
        failureModeDao.save(fM_A1);
        faultModeDao.save(enFM_A1);
        faultModeDao.save(enFM_A3);
        faultModeDao.save(exFM_A2);
        errorModeDao.save(eM_A1);
        return Response.ok().build();
    }

}
