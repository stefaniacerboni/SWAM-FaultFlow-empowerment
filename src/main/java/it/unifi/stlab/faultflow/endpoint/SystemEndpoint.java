package it.unifi.stlab.faultflow.endpoint;

import it.unifi.stlab.faultflow.businessLogic.AddSystemController;
import it.unifi.stlab.faultflow.dto.inputsystemdto.InputSystemDto;
import it.unifi.stlab.faultflow.exporter.PetriNetExportMethod;
import it.unifi.stlab.faultflow.exporter.XPNExporter;
import it.unifi.stlab.faultflow.exporter.strategies.OrderByComponentToXPN;
import it.unifi.stlab.faultflow.mapper.FaultTreeMapper;
import it.unifi.stlab.faultflow.mapper.SystemMapper;
import it.unifi.stlab.faultflow.model.knowledge.composition.System;
import it.unifi.stlab.faultflow.translator.PetriNetTranslator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

@Path("/system")
public class SystemEndpoint {

    @Inject
    AddSystemController addSystemController = new AddSystemController();

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSystem(InputSystemDto inputSystemDto) {
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        return Response.ok(FaultTreeMapper.systemToOutputSystem(sys)).build();
    }

    @POST
    @Path("/xpn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetriNetXPN(InputSystemDto inputSystemDto,
                                   @QueryParam("method") @DefaultValue("fa") String method) {
        PetriNetTranslator pnt = new PetriNetTranslator();
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        //TODO nome del file generatore casualmente + path configurabile in maniera più elegante attraverso un file di properties
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
    @Path("/load")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response persistSystem(InputSystemDto inputSystemDto) {
        System sys = SystemMapper.BddToSystem(inputSystemDto.getBdd());
        FaultTreeMapper.decorateSystem(inputSystemDto.getFaultTree(), sys);
        addSystemController.persistSystem(sys);
        return Response.ok(FaultTreeMapper.systemToOutputSystem(sys)).build();
    }

}
