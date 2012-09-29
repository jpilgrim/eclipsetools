property OG : "OmniGraffle Professional 5"
using terms from application "OmniGraffle Professional 5"
	
	
	tell application OG
		
		--ELEMENTS_ARE_CREATED_HERE--
		
		set li to layout info of canvas of front window
		set oldType to type of li
		set oldDirection to direction of li
		set type of li to hierarchical
		set direction of li to bottom to top
		layout selection of front window
		set type of li to oldType
		set direction of li to oldDirection
		
	end tell
	
	
	on createGeneralization(sub, super)
		tell application OG
			tell canvas of front window
				
				set generalization to make new line at end of graphics with properties {point list:{{30.46816, 23.92557}, {249.5318, 106.0747}}, line type:orthogonal, head type:"UMLInheritance"}
				
				set source of generalization to sub
				set destination of generalization to super
				
				return generalization
			end tell
		end tell
	end createGeneralization
	
	on createImplementation(sub, super)
		tell application OG
			tell canvas of front window
				set impl to make new line at end of graphics with properties {point list:{{30.46816, 23.92557}, {249.5318, 106.0747}}, line type:orthogonal, head type:"UMLInheritance", stroke pattern:1}
				
				set source of impl to sub
				set destination of impl to super
				
				return impl
				
			end tell
		end tell
	end createImplementation
	
	on createDependency(client, supplier)
		tell application OG
			tell canvas of front window
				set dependency to make new line at end of graphics with properties {point list:{{30.46816, 23.92557}, {249.5318, 106.0747}}, head type:"StickArrow", stroke pattern:1}
				
				set source of dependency to client
				set destination of dependency to supplier
				
				return dependency
				
			end tell
		end tell
	end createDependency
	
	on createAssoc(name, cardinality, io_source, io_dest)
		tell application OG
			tell canvas of front window
				set labelCard to make new shape at end of graphics with properties {fill:no fill, draws shadow:false, size:{24.0, 21.0}, autosizing:full, origin:{224.178787, 82.023254}, text:{text:cardinality as string, size:9, alignment:center}, draws stroke:false}
				set labelName to make new shape at end of graphics with properties {fill:no fill, draws shadow:false, size:{41.0, 24.0}, autosizing:full, origin:{123.362335, 42.700489}, text:{text:name as string, alignment:center}, draws stroke:false}
				set assoc to make new line at end of graphics with properties {point list:{{30.46816, 23.92557}, {249.5318, 106.0747}}, head type:"StickArrow"}
				set labelConnection of labelCard to assoc
				set labelPosition of labelCard to 0.9
				set labelOffset of labelCard to 8.0
				set labelConnection of graphic -2 to assoc
				set labelPosition of labelName to 0.5
				set labelOffset of labelName to 8.0
				
				set source of assoc to io_source
				set destination of assoc to io_dest
				
				return assoc
			end tell
		end tell
	end createAssoc
	
	on createClass(name, packageName, isAbstract, attributes, operations)
		tell application OG
			tell canvas of front window
				
				set nameFont to "Helvetica-Bold"
				if isAbstract then
					set nameFont to "Helvetica-BoldOblique"
				end if
				
				if packageName > "" then
					set nameCompartement to make new shape at end of graphics with properties {text placement:top, draws shadow:false, size:{99.0, 24.0}, autosizing:full, origin:{70.308998, 178.497986}, text:{{text:packageName as string, size:8, alignment:center}, {text:"
", alignment:center}, {text:name as string, font:nameFont, alignment:center}}}
				else
					set nameCompartement to make new shape at end of graphics with properties {text placement:top, draws shadow:false, size:{99.0, 24.0}, autosizing:full, origin:{70.308998, 178.497986}, text:{text:name as string, font:nameFont, alignment:center}}
					
				end if
				
				set attribCompartement to make new shape at end of graphics with properties {text placement:top, draws shadow:false, size:{99.0, 38.0}, autosizing:full, origin:{70.308998, 202.497986}, vertical padding:1, text:{attributes as list}}
				
				
				set operationCompartement to make new shape at end of graphics with properties {text placement:top, draws shadow:false, size:{99.0, 24.0}, origin:{70.308998, 240.497986}, autosizing:full, vertical padding:1, text:{operations as list}}
				
				set size of text of attribCompartement to 11
				set size of text of operationCompartement to 11
				
				set classBox to assemble {nameCompartement, attribCompartement, operationCompartement} table shape {3, 1}
				set connect to group only of classBox to true
				
				return classBox
			end tell
		end tell
	end createClass
	
	on createClassSimple(name, packageName, isAbstract, isContext)
		tell application OG
			tell canvas of front window
				
				set nameFont to "Helvetica-Bold"
				if isAbstract then
					set nameFont to "Helvetica-BoldOblique"
				end if
				
				set theColor to {0, 0, 0}
				if isContext then
					set theColor to {0.7, 0.7, 0.7}
				end if
				
				if packageName > "" then
					set nameCompartement to make new shape at end of graphics with properties {stroke color:theColor, text placement:top, draws shadow:false, size:{99.0, 24.0}, autosizing:full, origin:{70.308998, 178.497986}, text:{{text:packageName as string, size:8, alignment:center, color:theColor}, {text:"
", alignment:center}, {text:name as string, font:nameFont, alignment:center, color:theColor}}}
					
				else
					set nameCompartement to make new shape at end of graphics with properties {stroke color:theColor, text placement:top, draws shadow:false, size:{99.0, 24.0}, autosizing:full, origin:{70.308998, 178.497986}, text:{text:name as string, font:nameFont, alignment:center, color:theColor}}
					
				end if
				
				
				
				set classBox to assemble {nameCompartement} table shape {1, 1}
				set connect to group only of classBox to true
				
				return classBox
			end tell
		end tell
	end createClassSimple
	
	on createInterface(name, packageName, attributes, operations)
		tell application OG
			tell canvas of front window
				
				
				if packageName > "" then
					set nameCompartement to make new shape at end of graphics with properties {draws shadow:false, size:{99.0, 45.0}, autosizing:full, origin:{58.0, 65.0}, text:{{text:"«interface»", size:8, alignment:center}, {text:"
", alignment:center}, {text:packageName as string, size:8, alignment:center}, {text:"
", alignment:center}, {text:name as string, font:"Helvetica-Bold", alignment:center}}}
					
					
				else
					set nameCompartement to make new shape at end of graphics with properties {draws shadow:false, size:{99.0, 45.0}, autosizing:full, origin:{58.0, 65.0}, text:{{text:"«interface»", size:8, alignment:center}, {text:"
", alignment:center}, {text:name as string, font:"Helvetica-Bold", alignment:center}}}
					
				end if
				
				
				set attribCompartement to make new shape at end of graphics with properties {text placement:top, draws shadow:false, size:{99.0, 38.0}, autosizing:full, origin:{70.308998, 202.497986}, vertical padding:1, text:{attributes as list}}
				set operationCompartement to make new shape at end of graphics with properties {text placement:top, draws shadow:false, size:{99.0, 24.0}, origin:{70.308998, 240.497986}, autosizing:full, vertical padding:1, text:{operations as list}}
				
				set size of text of attribCompartement to 11
				set size of text of operationCompartement to 11
				
				set classBox to assemble {nameCompartement, attribCompartement, operationCompartement} table shape {3, 1}
				set connect to group only of classBox to true
				
				return classBox
			end tell
		end tell
	end createInterface
	
	on createInterfaceSimple(name, packageName, isContext)
		set theColor to {0, 0, 0}
		if isContext then
			set theColor to {0.7, 0.7, 0.7}
		end if
		
		tell application OG
			tell canvas of front window
				if packageName > "" then
					set nameCompartement to make new shape at end of graphics with properties {stroke color:theColor, draws shadow:false, size:{99.0, 45.0}, autosizing:full, origin:{58.0, 65.0}, text:{{text:"«interface»", size:8, alignment:center, color:theColor}, {text:"
", alignment:center}, {text:packageName as string, size:8, alignment:center, color:theColor}, {text:"
", alignment:center}, {text:name as string, font:"Helvetica-Bold", alignment:center, color:theColor}}}
					
					
				else
					set nameCompartement to make new shape at end of graphics with properties {stroke color:theColor, draws shadow:false, size:{99.0, 45.0}, autosizing:full, origin:{58.0, 65.0}, text:{{text:"«interface»", size:8, alignment:center, color:theColor}, {text:"
", alignment:center}, {text:name as string, font:"Helvetica-Bold", alignment:center, color:theColor}}}
				end if
				
				
				set classBox to assemble {nameCompartement} table shape {1, 1}
				set connect to group only of classBox to true
				
				return classBox
			end tell
		end tell
	end createInterfaceSimple
	
	on createDiagramNote(packageName, configInfo)
		tell application OG
			tell canvas of front window
				set newNote to make new shape at end of graphics with properties {draws shadow:false, size:{82.0, 82.0}, name:"NoteShape", autosizing:vertically only, origin:{10.0, 10.0}, text:{{text:"package
"}, {text:packageName as string, font:"Helvetica-Bold", size:13}, {text:"
"}, {text:configInfo as string, size:10}}}
				return newNote
			end tell
		end tell
	end createDiagramNote
	
	
end using terms from
