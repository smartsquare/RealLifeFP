package reallifefp;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class Java8Test {

    private List<File> files;

    @Before
    public void setup()
        throws Exception {
        files = Arrays.asList( new File( "src/test/resources" ).listFiles() );
    }

    @Test
    public void filter()
        throws Exception {
        List<File> directories = files.stream()
            .filter( file -> file.isDirectory() )
            .collect( Collectors.toList() );

        assertThat( directories, hasSize( 2 ) );
    }

    @Test
    public void find_success()
        throws Exception {
        Optional<File> foobar = files.stream()
            .filter( file -> file.getName().endsWith( ".foobar" ) )
            .findAny();
        assertFalse( foobar.isPresent() );

        Optional<File> foo = files.stream()
            .filter( file -> file.getName().endsWith( ".foo" ) )
            .findAny();
        assertTrue( foo.isPresent() );
    }

    @Test
    public void all_and_any()
        throws Exception {
        boolean allDirectories = files.stream().allMatch( file -> file.isDirectory() );
        assertFalse( allDirectories );

        boolean anyDirectories = files.stream().anyMatch( file -> file.isDirectory() );
        assertTrue( anyDirectories );
    }

    @Test
    public void transform()
        throws Exception {
        List<String> names = files.stream()
            .map( file -> file.getName() )
            .collect( Collectors.toList() );

        assertThat( names, hasSize( files.size() ) );
    }

    @Test
    public void index()
        throws Exception {
        Map<Boolean, List<File>> isDirectoryMap = files.stream()
            .collect( Collectors.groupingBy( File::isDirectory ) );

        assertThat( isDirectoryMap.get( true ), hasSize( 2 ) );
        assertThat( isDirectoryMap.get( false ), hasSize( 3 ) );
    }
}
