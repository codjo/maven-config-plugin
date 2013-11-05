/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.maven.mojo.config;
import net.codjo.maven.common.test.PathUtil;
import java.io.File;
import java.io.IOException;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.codehaus.plexus.util.FileUtils;
/**
 *
 */
public abstract class AgfMojoTestCase extends AbstractMojoTestCase {
    /**
     * @noinspection StaticNonFinalField
     */
    private static AgfMojoTestCase singleton = null;
    private File pomFile;
    private File targetDir;
    private MavenProjectMock project;


    public void setupEnvironment(String pomFilePath)
          throws IOException {
        this.pomFile = getInputFile(pomFilePath);

        targetDir = new File(PathUtil.findTargetDirectory(getClass()),
                             "test-harness/" + getClass().getName());

        if (targetDir.exists()) {
            FileUtils.deleteDirectory(targetDir);
        }

        targetDir.mkdirs();
    }


    public File getInputFile(String file) {
        return PathUtil.find(getClass(), file);
    }


    public File getOutputFile(String file) {
        return PathUtil.find(getClass(), file);
    }


    protected void deleteOutputFile(String fileName) {
        new File(getPomFile().getParent(), fileName).delete();
    }


    public File getPomFile() {
        return pomFile;
    }


    public File getTargetDir() {
        return targetDir;
    }


    public File getTargetFile(String fileName) {
        return new File(getTargetDir(), fileName);
    }


    public MavenProjectMock getProject() {
        return project;
    }


    static void setProject(MavenProjectMock project) {
        singleton.project = project;
        project.setFile(singleton.getPomFile());
    }


    public Artifact createArtifact(String groupId, String artifactId, String scope,
                                   String type) {
        DefaultArtifact defaultArtifact =
              new DefaultArtifact(groupId, artifactId,
                                  VersionRange.createFromVersion("1.0"), scope, type, "main", null);
        defaultArtifact.setFile(getInputFile("repository/" + groupId + "/" + artifactId
                                             + "/1.0/" + artifactId + "-1.0." + type));
        return defaultArtifact;
    }


    protected Mojo lookupMojo(String goal) throws Exception {
        singleton = this;
        return lookupMojo(goal, getPomFile());
    }
}
