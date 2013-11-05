/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.maven.mojo.config;
import net.codjo.maven.common.test.Directory;
import net.codjo.maven.common.test.FileUtil;
import net.codjo.maven.common.test.PathUtil;
import org.apache.maven.plugin.Mojo;
/**
 *
 */
public class ConfigMojoTest extends AgfMojoTestCase {
    private static final String CONFIG_1 = "/mojos/basic/output/config1.properties";
    private static final String CONFIG_2 = "/mojos/basic/output/config2.properties";


    public void test_basic() throws Exception {
        setupEnvironment("/mojos/basic/basic-pom.xml");

        Mojo mojo = lookupMojo("config");

        getProject().setName("projectName");
        getProject().setGroupId("group-id");
        getProject().getProperties().put("samDirectory", "${project.basedir}/backup");

        deleteOutputFile(CONFIG_1);
        deleteOutputFile(CONFIG_2);

        mojo.execute();

        assertEquals(("my.property=projectname\r\n"
                      + "my.basedir=" + getTargetDirPath() + "/test-classes/mojos/basic\r\n"
                      + "my.basedir.2=" + getTargetDirPath() + "/test-classes/mojos/basic\r\n"
                      + "my.basedir.3=" + getTargetDirPath() + "/test-classes/mojos/basic/backup\r\n"

        ).toLowerCase(),
                     FileUtil.loadContent(getOutputFile(CONFIG_1)).toLowerCase());

        assertEquals("other.property=group-id\r\n",
                     FileUtil.loadContent(getOutputFile(CONFIG_2)));
    }


    private String getTargetDirPath() {
        Directory targetDirectory = PathUtil.findTargetDirectory(ConfigMojo.class);
        return targetDirectory.getPath().replaceAll("\\\\", "/");
    }
}
