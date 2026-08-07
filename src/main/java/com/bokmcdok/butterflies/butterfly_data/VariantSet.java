package com.bokmcdok.butterflies.butterfly_data;

/**
 * Holds a list of variants.
 * @param baseVariant The base variant of the butterfly
 * @param coldVariant The cold variant of the butterfly
 * @param mateVariant The mate variant of the butterfly
 * @param warmVariant The warm variant of the butterfly
 * @param agedVariant The aged variant of the butterfly
 */
public record VariantSet(SpeciesId baseVariant,
                         SpeciesId coldVariant,
                         SpeciesId mateVariant,
                         SpeciesId warmVariant,
                         SpeciesId agedVariant) {
}
