package com.decision.swagger.codegen.typescript.angular2;

import io.swagger.codegen.CodegenConfig;
import io.swagger.codegen.CodegenParameter;
import io.swagger.codegen.SupportingFile;
import io.swagger.codegen.languages.TypeScriptAngularClientCodegen;
import io.swagger.models.properties.*;

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

    @Override
    public void processOpts() {
        super.processOpts();
        supportingFiles.clear();
        supportingFiles.add(new SupportingFile("model.d.mustache", modelPackage().replace('.', File.separatorChar), "model.d.ts"));
    }


    public TypescriptAngular2Generator() {
        super();
        this.outputFolder = "generated-code/typescript-angular2";
        typeMapping.put("Date", "Date");
        typeMapping.put("any", "any");
        embeddedTemplateDir = templateDir = "typescript-angular2";
        apiPackage = "api";
        modelPackage = "model";
    }

    @Override
    public String getTypeDeclaration(Property p) {
        Property inner;
        if(p instanceof ArrayProperty) {
            ArrayProperty mp1 = (ArrayProperty)p;
            inner = mp1.getItems();
            if(inner instanceof ByteArrayProperty){
                return "Int8Array";
            }
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
        String type = swaggerType;
        if (!swaggerType.startsWith("Array")) {
            if (typeMapping.containsKey(swaggerType)) {
                type = typeMapping.get(swaggerType);
                if (languageSpecificPrimitives.contains(type))
                    return type;
            } else
                type = "model." + swaggerType;
        }
        return type;
    }

    @Override
    public void postProcessParameter(CodegenParameter parameter) {
        super.postProcessParameter(parameter);
        parameter.dataType = addModelPrefix(parameter.dataType);
    }
}
