package com.decision.swagger.codegen.typescript.angular2;

import io.swagger.codegen.CodegenConfig;
import io.swagger.codegen.CodegenParameter;
import io.swagger.codegen.SupportingFile;
import io.swagger.codegen.languages.TypeScriptAngularClientCodegen;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.FileProperty;
import io.swagger.models.properties.MapProperty;
import io.swagger.models.properties.Property;

import java.io.File;

public class TypescriptAngular2Generator extends TypeScriptAngularClientCodegen implements CodegenConfig {

    @Override
    public String getName() {
        return "typescript-angular2";
    }

    @Override
    public String getHelp() {
        return "Generates a TypeScript Angular2 client library.";
    }

    public TypescriptAngular2Generator() {
        super();
        this.outputFolder = "generated-code/typescript-angular2";
        typeMapping.put("Date","Date");
        embeddedTemplateDir = templateDir = "typescript-angular2";
        apiPackage = "api";
        modelPackage = "model";
        supportingFiles.remove(supportingFiles.get(supportingFiles.size() - 1));
        supportingFiles.add(new SupportingFile("model.d.mustache", modelPackage().replace('.', File.separatorChar), "model.d.ts"));
    }

    @Override
    public String getTypeDeclaration(Property p) {
        Property inner;
        if(p instanceof ArrayProperty) {
            ArrayProperty mp1 = (ArrayProperty)p;
            inner = mp1.getItems();
            return this.getSwaggerType(p) + "<" + this.getTypeDeclaration(inner) + ">";
        } else if(p instanceof MapProperty) {
            MapProperty mp = (MapProperty)p;
            inner = mp.getAdditionalProperties();
            return "{ [key: string]: " + this.getTypeDeclaration(inner) + "; }";
        } else {
            return p instanceof FileProperty ?"any":super.getTypeDeclaration(p);
        }
    }

    @Override
    public String getSwaggerType(Property p) {
        String swaggerType = super.getSwaggerType(p);
        return addModelPrefix(swaggerType);
    }

    private String addModelPrefix(String swaggerType) {
        String type = null;
        if (typeMapping.containsKey(swaggerType)) {
            type = typeMapping.get(swaggerType);
            if (languageSpecificPrimitives.contains(type))
                return type;
        } else
            type = "model." + swaggerType;
        return type;
    }

    @Override
    public void postProcessParameter(CodegenParameter parameter) {
        super.postProcessParameter(parameter);
        parameter.dataType = addModelPrefix(parameter.dataType);
    }
}