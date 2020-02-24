package xyz.annorit24.simplequestscore.utils.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 *
 * @author Annorit24
 * Created on 28/12/2019
 */
public final class EventsUtils {

    public static void loadEventClasses() {
        try {
            List a = getClassesFromPackage("org.bukkit.event");
            a.forEach(Object::getClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Attempts to list all the classes in the specified package as determined     *
     * by the context class loader…
     *
     * @param pckgname the package name to search
     * @return a list of classes that exist within that package
     * @throws ClassNotFoundException if something went wrong
     *
     */
    public static List getClassesFromPackage(String pckgname) throws ClassNotFoundException {
        ArrayList result = new ArrayList();
        ArrayList<File> directories = new ArrayList();
        HashMap packageNames = null;

        try {
            for (URL jarURL : ((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs()) {

                getClassesInSamePackageFromJar(result, pckgname, jarURL.getPath().replace("%20"," "));
                String path = pckgname;
                Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);

                File directory = null;

                while (resources.hasMoreElements()) {
                    String path2 = resources.nextElement().getPath();
                    directory = new File(URLDecoder.decode(path2, "UTF-8"));
                    directories.add(directory);
                }

                if (packageNames == null) {
                    packageNames = new HashMap();
                }

                packageNames.put(directory, pckgname);
            }

        } catch (NullPointerException x) {
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Null pointer exception)");
        } catch (UnsupportedEncodingException encex) {
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Unsupported encoding)");
        } catch (IOException ioex) {
            throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + pckgname);
        }

        for (File directory : directories) {
            if (directory.exists()) {
                String[] files = directory.list();

                for (String file : files) {
                    if (file.endsWith(".class")) {
                        try {
                            result.add(Class.forName(packageNames.get(directory).toString() + '.' + file.substring(0, file.length() - 6)));
                        } catch (Throwable e) {
                        }
                    }
                }
            } else {
                throw new ClassNotFoundException(pckgname + " (" + directory.getPath() + ") does not appear to be a valid package");
            }
        }
        return result;
    }

    /**
     * Returns the list of classes in the same directories as Classes in
     * classes.
     *
     * @param result result
     * @param packageName package name
     * @param jarPath jarPath
     */
    private static void getClassesInSamePackageFromJar(List result, String packageName, String jarPath) {
        JarFile jarFile = null;

        try {
            jarFile = new JarFile(jarPath);
            Enumeration<JarEntry> en = jarFile.entries();

            while (en.hasMoreElements()) {

                JarEntry entry = en.nextElement();
                String entryName = entry.getName();
                packageName = packageName.replace('.', '/');

                if (entryName != null && entryName.endsWith(".class") && entryName.startsWith(packageName)) {

                    try {
                        Class entryClass = Class.forName(entryName.substring(0, entryName.length() - 6).replace('/', '.'));

                        if (entryClass != null) {
                            result.add(entryClass);
                        }
                    } catch (Throwable e) {
                        
                    }
                }
            }
        } catch (Exception e) {
        
        } finally {
            try {
                if (jarFile != null) {
                    jarFile.close();
                }
            } catch (Exception e) {

            }
        }
    }

    public static Player getPlayerFromEvent(Event event){
        try {
            Method m = event.getClass().getMethod("getPlayer");
            return (Player) m.invoke(event);
        } catch (Exception ignored) {
            try {
                Method m = event.getClass().getMethod("getEntity");
                Entity entity = (Entity) m.invoke(event);
                if(entity instanceof Player){
                    return (Player) entity;
                }else{
                    return null;
                }
            } catch (Exception ignored1) {
            }
        }
        return null;
    }
}
