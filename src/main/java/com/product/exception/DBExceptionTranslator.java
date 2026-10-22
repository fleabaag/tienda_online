package com.product.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

public final class DBExceptionTranslator {

    private DBExceptionTranslator() {
        
    }

    /**
     * Traduce los errores de la 2ª capa (índices únicos y triggers) a ApiException.
     *
     * @param e          excepción lanzada por Spring Data / JDBC
     * @param defaultMsg mensaje a usar si no se reconoce el error
     */
    public static ApiException translate(DataAccessException e, String defaultMsg) {
        Throwable cause = e.getMostSpecificCause();
        String msg = cause != null ? cause.getMessage() : e.getLocalizedMessage();

        if (msg != null) {
            if (msg.contains("ux_category"))
                return new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está en uso.");
            if (msg.contains("ux_tag"))
                return new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está en uso.");
            if (msg.contains("trg_parent_self"))
                return new ApiException(HttpStatus.BAD_REQUEST, "Una categoría no puede ser padre de sí misma.");
            if (msg.contains("trg_parent_invalid"))
                return new ApiException(HttpStatus.CONFLICT, "La categoría padre no existe o está inactiva.");
            if (msg.contains("trg_parent_cycle"))
                return new ApiException(HttpStatus.CONFLICT, "La asignación del padre generaría un ciclo.");
            if (msg.contains("trg_has_children"))
                return new ApiException(HttpStatus.CONFLICT,
                        "No se puede eliminar la categoría porque tiene categorías hijas.");
            if (msg.contains("trg_has_active_children"))
                return new ApiException(HttpStatus.CONFLICT,
                        "No se puede desactivar la categoría porque tiene categorías hijas activas.");
        }
        return new ApiException(HttpStatus.BAD_REQUEST, defaultMsg);
    }
}