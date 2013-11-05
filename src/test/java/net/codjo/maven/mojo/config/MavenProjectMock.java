package net.codjo.maven.mojo.config;
import org.apache.maven.model.Model;
import org.apache.maven.project.MavenProject;
/**
 *
 */
public class MavenProjectMock extends MavenProject {

    public MavenProjectMock() {
        super(new Model());
        AgfMojoTestCase.setProject(this);
    }
}