/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.maven.mojo.config;

import net.codjo.maven.common.resources.FilteredManager;
import java.io.IOException;
import java.util.Collections;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
/**
 * Instancie les parties variables dans les fichiers de config.
 *
 * @goal config
 * @phase generate-sources
 */
public class ConfigMojo extends AbstractMojo {
    /**
     * Le répertoire qui contient la config à copier.
     *
     * @parameter expression="${project.basedir}/src/config"
     * @required
     * @noinspection UNUSED_SYMBOL
     */
    private String inputDirectory;
    /**
     * The output directory into which to copy the resources.
     *
     * @parameter expression="${project.basedir}/target/config"
     * @required
     * @noinspection UNUSED_SYMBOL
     */
    private String outputDirectory;
    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     * @noinspection UNUSED_SYMBOL
     */
    private MavenProject project;


    public void execute() throws MojoExecutionException {
        Resource config = new Resource();
        config.setDirectory(inputDirectory);
        config.setFiltering(true);

        getLog().info("Loading config from '" + inputDirectory + "'");
        getLog().info("Generate config into '" + outputDirectory + "'");
        try {
            FilteredManager filteredManager = new FilteredManager(project);
            filteredManager.copyResources(Collections.singletonList(config), outputDirectory);
        }
        catch (IOException ioException) {
            throw new MojoExecutionException(
                  "Cannot execute the maven-config-plugin.", ioException);
        }
    }
}
