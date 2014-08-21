package com.tatianomnom.choozorro.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.tatianomnom.choozorro.db.Foo;
import com.tatianomnom.choozorro.db.FooDao;
import com.tatianomnom.choozorro.service.StringIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TODO add description
 */
@Path("/polls")
public class PollResource {

    @Context
    UriInfo uriInfo;

    private final StringIdGenerator idGenerator;

    private final FooDao fooDao;

    @Inject
    public PollResource(StringIdGenerator idGenerator, FooDao fooDao) {
        this.idGenerator = idGenerator;
        this.fooDao = fooDao;
    }

    private static final Logger logger = LogManager.getLogger();

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(PollCommand pollCommand) throws URISyntaxException{
        logger.debug(pollCommand.toString());

        String id = idGenerator.generateId();

        fooDao.addFoo(new Foo(5, "Nom"));

        logger.debug(fooDao.findFoo(5).toString());

        logger.debug(uriInfo.getAbsolutePath());
        //TODO try catch... how to handle it properly? can it happen at all?
        return Response.seeOther(new URI(uriInfo.getAbsolutePath() + "/" + id))
                .cookie(new NewCookie("chz_usr", id.toUpperCase()))
                .build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response get(@PathParam("id") String id,
                        @CookieParam(value = "chz_usr") String token) {
        PollCommand pollCommand = new PollCommand("aaa", Arrays.asList("a", "b"), null);
        logger.debug(id);
        logger.debug(token);
        return Response.status(Response.Status.OK).entity(pollCommand).build();
    }

}
