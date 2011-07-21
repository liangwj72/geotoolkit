/*
 *    Geotoolkit.org - An Open Source Java GIS Toolkit
 *    http://www.geotoolkit.org
 *
 *    (C) 2011, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2011, Geomatys
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotoolkit.internal.jaxb;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.opengis.metadata.Identifier;
import org.opengis.metadata.citation.Citation;

import org.geotoolkit.xml.XLink;
import org.geotoolkit.xml.IdentifierMap;
import org.geotoolkit.xml.IdentifierSpace;
import org.geotoolkit.util.logging.Logging;
import org.geotoolkit.util.Utilities;
import org.geotoolkit.internal.Citations;


/**
 * Wraps a {@link XLink}, {@link UUID} or other objects as an identifier in the {@link IdentifierMap}.
 * The {@linkplain #authority} is typically an instance of {@link IdentifierAuthority}. The value is
 * an object of a type constrained by the authority.
 *
 * @param  <T> The value type, typically {@link XLink}, {@link UUID} or {@link String}.
 *
 * @author Martin Desruisseaux (Geomatys)
 * @version 3.19
 *
 * @since 3.19
 * @module
 */
public final class IdentifierAdapter<T> implements Identifier {
    /**
     * The authority, typically as an {@link IdentifierAuthority) instance.
     * This authority is not allowed to be null.
     *
     * @see #getAuthority()
     */
    private final IdentifierSpace<T> authority;

    /**
     * The identifier value. The identifier {@linkplain #getCode() code} will be the
     * {@linkplain Object#toString() string representation} of this value, if non-null.
     */
    T value;

    /**
     * Creates a new adapter for the given authority and identifier value.
     *
     * @param authority The identifier authority (can not be null).
     * @param value The identifier value.
     */
    public IdentifierAdapter(final IdentifierSpace<T> authority, final T value) {
        this.authority = authority;
        this.value = value;
    }

    /**
     * Creates an identifier from a text value. This method creates an instance of
     * {@code IdentifierAdapter} if the given authority is one of the "special"
     * authorities declared in the {@link IdentifierSpace} interface. Otherwise
     * a plain {@link IdentifierMapEntry} is created.
     *
     * @param authority The authority, typically as one of the {@link IdentifierSpace} constants.
     * @param code The identifier code to parse.
     */
    static Identifier parse(final Citation authority, final String code) {
        if (authority instanceof IdentifierAuthority) {
            switch (((IdentifierAuthority) authority).ordinal) {
                case IdentifierAuthority.ID: {
                    return new IdentifierAdapter<String>(IdentifierSpace.ID, code);
                }
                case IdentifierAuthority.UUID: {
                    try {
                        return new IdentifierAdapter<UUID>(IdentifierSpace.UUID, UUID.fromString(code));
                    } catch (IllegalArgumentException e) {
                        parseFailure(e);
                        break;
                    }
                }
                case IdentifierAuthority.HREF: {
                    final URI href;
                    try {
                        href = new URI(code);
                    } catch (URISyntaxException e) {
                        parseFailure(e);
                        break;
                    }
                    return new IdentifierAdapter<URI>(IdentifierSpace.HREF, href);
                }
                case IdentifierAuthority.XLINK: {
                    final URI href;
                    try {
                        href = new URI(code);
                    } catch (URISyntaxException e) {
                        parseFailure(e);
                        break;
                    }
                    final XLink xlink = new XLink();
                    xlink.setHRef(href);
                    return new IdentifierAdapter<XLink>(IdentifierSpace.XLINK, xlink);
                }
            }
        }
        return new IdentifierMapEntry(authority, code);
    }

    /**
     * Invoked by {@link #parse(Citation, String)} when a string can not be parsed. This is
     * considered as a non-fatal error, because the parse method can fallback on the generic
     * {@link IdentifierMapEntry} in such case.
     */
    private static void parseFailure(final Exception e) {
        Logging.recoverableException(IdentifierMap.class, "put", e);
    }

    /**
     * Adds this identifier to the given map.
     *
     * @param map The map in which to write this identifier.
     */
    public void putInto(final IdentifierMap map) {
        if (!(map instanceof IdentifierMapAdapter) || !((IdentifierMapAdapter) map).putSpecialized(this)) {
            map.putSpecialized(authority, value); // Fallback if the above 'putSpecialized' can't be used.
        }
    }

    /**
     * Returns the authority specified at construction time.
     */
    @Override
    public Citation getAuthority() {
        return authority;
    }

    /**
     * Returns a string representation of the identifier given at construction time,
     * or {@code null} if none.
     */
    @Override
    public String getCode() {
        return (value != null) ? value.toString() : null;
    }

    /**
     * Returns a hash code value for this identifier.
     */
    @Override
    public int hashCode() {
        return Utilities.hash(value, authority.hashCode());
    }

    /**
     * Compares this identifier with the given object for equality.
     *
     * @param other The object to compare with this identifier for equality.
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof IdentifierAdapter<?>) {
            final IdentifierAdapter<?> that = (IdentifierAdapter<?>) other;
            return Utilities.equals(authority, that.authority) &&
                   Utilities.equals(value, that.value);
        }
        return false;
    }

    /**
     * Returns a string representation of this identifier.
     */
    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder("Identifier[");
        format(buffer, authority, getCode());
        return buffer.append(']').toString();
    }

    /**
     * Formats the given (authority, code) par value in the given buffer.
     */
    static void format(final StringBuilder buffer, final Citation authority, final String code) {
        buffer.append(Citations.getIdentifier(authority)).append('=');
        final boolean quote = (code != null) && (code.indexOf('[') < 0);
        if (quote) buffer.append('“');
        buffer.append(code);
        if (quote) buffer.append('”');
    }
}
