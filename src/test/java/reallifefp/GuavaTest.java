package reallifefp;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class GuavaTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private List<File> files;

    @Before
    public void setup()
        throws Exception {
        files = Arrays.asList( new File( "src/test/resources" ).listFiles() );
    }

    @Test
    public void filter()
        throws Exception {
        Iterable<File> directories = Iterables.filter( files, new Predicate<File>() {
            @Override
            public boolean apply( File input ) {
                return input.isDirectory();
            }
        } );

        assertThat( Lists.newArrayList( directories ), hasSize( 2 ) );
    }

    @Test
    public void find_fails()
        throws Exception {
        expectedException.expect( NoSuchElementException.class );
        Iterables.find( files, new Predicate<File>() {
            @Override
            public boolean apply( File input ) {
                return input.getName().endsWith( ".foobar" );
            }
        } );
    }

    @Test
    public void find_success()
        throws Exception {
        File file = Iterables.find( files, new Predicate<File>() {
            @Override
            public boolean apply( File input ) {
                return input.getName().endsWith( ".foo" );
            }
        } );
        assertNotNull( file );
    }

    @Test
    public void tryFind()
        throws Exception {
        Optional<File> file = Iterables.tryFind( files, new Predicate<File>() {
            @Override
            public boolean apply( File input ) {
                return input.getName().endsWith( ".foobar" );
            }
        } );
        assertFalse( file.isPresent() );

        file = Iterables.tryFind( files, new Predicate<File>() {
            @Override
            public boolean apply( File input ) {
                return input.getName().endsWith( ".foo" );
            }
        } );
        assertTrue( file.isPresent() );
    }

    @Test
    public void all_and_any()
        throws Exception {
        Predicate<File> isDirectory = new Predicate<File>() {
            @Override
            public boolean apply( File input ) {
                return input.isDirectory();
            }
        };

        boolean allDirectories = Iterables.all( files, isDirectory );
        assertFalse( allDirectories );

        boolean anyDirectories = Iterables.any( files, isDirectory );
        assertTrue( anyDirectories );
    }

    @Test
    public void transform()
        throws Exception {
        Iterable<String> names = Iterables.transform( files, new Function<File, String>() {
            @Override
            public String apply( File input ) {
                return input.getName();
            }
        } );

        assertThat( Lists.newArrayList( names ), hasSize( 4 ) );
    }

}
