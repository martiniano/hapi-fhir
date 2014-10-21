package ca.uhn.fhir.validation;

/*
 * #%L
 * HAPI FHIR - Core Library
 * %%
 * Copyright (C) 2014 University Health Network
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import ca.uhn.fhir.model.dstu.resource.OperationOutcome;

/**
 * Encapsulates the results of validation
 *
 * @see ca.uhn.fhir.validation.FhirValidator
 * @since 0.7
 */
public class ValidationResult {
    private OperationOutcome myOperationOutcome;

    private ValidationResult(OperationOutcome myOperationOutcome) {
        this.myOperationOutcome = myOperationOutcome;
    }

    public static ValidationResult valueOf(OperationOutcome myOperationOutcome) {
        return new ValidationResult(myOperationOutcome);
    }

    public OperationOutcome getOperationOutcome() {
        return myOperationOutcome;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "myOperationOutcome=" + myOperationOutcome +
                ", description='" + toDescription() + '\'' +
                '}';
    }

    private String toDescription() {
        StringBuilder b = new StringBuilder(100);
        if (myOperationOutcome != null) {
            OperationOutcome.Issue issueFirstRep = myOperationOutcome.getIssueFirstRep();
            b.append(issueFirstRep.getDetails().getValue());
            b.append(" - ");
            b.append(issueFirstRep.getLocationFirstRep().getValue());
        }
        return b.toString();
    }

    /**
     * Was the validation successful
     * @return true if the validation was successful
     */
    public boolean isSuccessful() {
        return myOperationOutcome == null || myOperationOutcome.getIssue().isEmpty();
    }
}