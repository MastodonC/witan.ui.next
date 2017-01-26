(ns witan.ui.strings
  (:require [clojure.string])
  (:require-macros
   [cljs-log.core :as log]))

(def strings
  {:string/name                           "Name"
   :string/file-name                      "File Name"
   :string/file-type                      "File Type"
   :string/file-provenance-source         "Source"
   :string/forecast-in-progress-text      "We're currently building a new version of this projection for you. You can download your data from the 'Output' section when this message disappears."
   :string/sign-in-failure                "There was a problem with your details. Please try again."
   :string/upload-new-data                "Upload new data"
   :string/upload-new-data-desc           "Upload to securely store, share or visualise your data"
   :string/forgotten-instruction          "Please enter your email address. If it matches one in our system we'll send you reset instructions."
   :string/browser-upload-select-existing "Please select the existing data item that you want to update"
   :string/use-data-item                  "Use"
   :string/pw-input-brief                 "Choose your input data"
   :string/confirm-email                  "Confirm email"
   :string/input-intro                    "To generate your projection, choose which data should go into your model from the options below. You can upload your own data, or choose from any datasets already in the system."
   :string/witan                          "Witan"
   :string/view-edit-forecast             "View/Edit this projection"
   :string/yesterday                      "Yesterday"
   :string/model                          "Model"
   :string/reset-submitted                "Thanks. Your password reset request has been received."
   :string/back                           "Back"
   :string/revert-forecast                "Revert changes"
   :string/please-wait                    "Please Wait..."
   :string/error                          "Error"
   :string/choose-file                    "Choose File"
   :string/public-only                    "Public only"
   :string/upload-data-public-warning     "As this is a public projection, any data you upload will also become public."
   :string/optional                       "(optional)"
   :string/no-model-properties            "This model has no properties to configure"
   :string/forecast-name                  "Name"
   :string/changed                        "Changed"
   :string/model-publisher                "Publisher"
   :string/processing-account             "Creating account..."
   :string/filter                         "Filter"
   :string/forgotten-password             "Forgotten Password"
   :string/browser-no-file-selected       "No file selected..."
   :string/witan-title                    "Witan for London"
   :string/help                           "Help"
   :string/no-input-specified             "No data input specified."
   :string/download                       "Download"
   :string/downloading                    "Downloading"
   :string/forecast-changes-text          "Okay, we've recorded your changes. When you're ready, hit 'Run this projection' to generate your new outputs for download."
   :string/sign-up-failure                "There was a problem signing-up with the provided details. Please check your invite token and try again."
   :string/output-extra-info              "The high level of precision of the figures in this data should not be mistaken for a declaration of accuracy. Users should be aware that significant levels of uncertainty exist in all of the outputs of these models. When publishing data for public use, it is recommended that numbers should be rounded and age groups should be aggregated."
   :string/forecast-lastmodified          "Last Modified"
   :string/please-select-data-input       "Please select a data input for this category."
   :string/create-new-forecast            "Run this projection"
   :string/new-forecast-desc-placeholder  "What should this projection be used for? Who should use it?"
   :string/browser-upload-option-existing "This is an updated version of an existing data item"
   :string/pw-output-brief                "Download your data"
   :string/input                          "Input"
   :string/output-intro                   "Once the model's calculations have been completed and your output data is available, you will be able to download it here. "
   :string/properties                     "Properties"
   :string/create-account-header          "Need an account?"
   :string/forecast-version               "Version"
   :string/view                           "View"
   :string/api-failure                    "Sorry, we're having a problem with the service. Please try again. If the problem persists, please contact us at witan@mastodonc.com"
   :string/password-no-match              "The provided passwords do not match."
   :string/forecast-type                  "Type"
   :string/refresh-now                    "Refresh"
   :string/model-intro                    "Your projection feeds your chosen input data into the model to below to generate your data. Download it at the next step."
   :string/thanks                         "Thanks"
   :string/forecast-public?               "Public?"
   :string/upload                         "Upload"
   :string/downloads                      "Downloads"
   :string/new-forecast                   "Create New Projection"
   :string/create-account                 "Create Account"
   :string/email                          "Email"
   :string/missing-required-inputs        "Some inputs are still missing data. Before you can save this projection, please select or upload appropriate data for the corresponding inputs."
   :string/email-no-match                 "The provided email addresses do not match."
   :string/creating-forecast              "Please wait whilst we update this projection..."
   :string/new-forecast-name-placeholder  "Enter a name for this projection"
   :string/search                         "Search"
   :string/sign-up-token                  "Invite code"
   :string/forecast-owner                 "Owner"
   :string/forecast-public?-explain       "Tick this box to make the Projection visible to everyone"
   :string/no-description-provided        "(No description has been provided.)"
   :string/upload-success                 "Upload successful"
   :string/in-progress                    "In-Progress"
   :string/progress                       "Progress"
   :string/forgotten-question             "forgotten your password?"
   :string/witan-tagline                  "Make more sense of your city"
   :string/browser-upload-option-new      "This is a brand new data item"
   :string/browser-upload-select-new      "Please enter a name for the new data item"
   :string/forecast-desc                  "Description"
   :string/sign-in                        "Sign In"
   :string/new                            "New"
   :string/create                         "Create"
   :string/in-progress-no-downloads       "Sorry, we're still running the projection to generate your data. This can take several minutes so please check again shortly."
   :string/upload-data-public-explain     "Tick this box to make the data visible to everyone (public)"
   :string/password                       "Password"
   :string/created                        "Created"
   :string/created-at                     "Created at"
   :string/confirm-password               "Confirm password"
   :string/browser-upload-error           "An error occurred whilst trying to upload the file. Please try again and if this problem persists, contact us."
   :string/today                          "Today"
   :string/reset-password                 "Reset Password"
   :string/logout                         "Log Out"
   :string/data-items                     "data items"
   :string/default-brackets               "(default)"
   :string/signing-in                     "Signing in..."
   :string/settings                       "Settings"
   :string/forecast                       "Projection"
   :string/create-account-info            "If you have an invite code you can create your account below:"
   :string/public                         "Public"
   :string/pw-model-brief                 "See how the model works"
   :string/output                         "Output"
   :string/browser-choose-data            "Choose from existing data or upload your own new data"
   :string/new-version-no-downloads       "Once you have run your first projection, your data will be available here. Start this at the 'Inputs' section"
   :string/about-model                    "About this model"
   :string/browser-upload-completes       "Please wait whilst your upload completes..."
   :string/superseded                     "Superseded"
   :string/tooltip-workspace              "Browse your workspaces"
   :string/tooltip-data                   "Browse your data sets"
   :string/tooltip-logout                 "Log out from your account"
   :string/tooltip-help                   "Get help"
   :string/tooltip-request-to-share       "Request some data be shared with you or a group you belong to"
   :string/workspace-noun-plural          "Workspaces"
   :string/workspace-dash-title           :string/workspace-noun-plural
   :string/data                           "Data"
   :string/go-to                          "Go to"
   :string/go-to-data                     [:string/go-to :string/data]
   :string/data-dash-title                "Data Sets"
   :string/workspace-dash-filter          ["Filter your" :string/workspace-noun-plural]
   :string/data-dash-filter               "Filter your data sets"
   :string/workspace-data-view            "Data"
   :string/workspace-config-view          "Configuration"
   :string/workspace-history-view         "History"
   :string/create-workspace-title         "Create a new workspace"
   :string/create-workspace-subtitle      "A workspace contains models and visualisations, configured how you want them"
   :string/create-workspace-name          "Workspace name"
   :string/create-workspace-name-ph       "Enter a name for this workspace"
   :string/create-workspace-desc          "Description"
   :string/create-workspace-desc-ph       "What will this workspace be used for?"
   :string/create-workspace-error         "An error occurred whilst trying to create this workspace. Please try again."
   :string/workspace-404-error            "Unable to find a Workspace at this address."
   :string/workspace-empty                "This workspace is empty!"
   :string/workspace-add-model            "Add Model"
   :string/workspace-select-a-model       "Please start by selecting a model:"
   :string/data-empty-catalog             "No data inputs required."
   :string/config-empty-catalog           "No configuration required."
   :string/run                            "Run"
   :string/running                        "Running"
   :string/no-viz-selected                "Please select the data you'd like to visualise!"
   :string/no-viz-selected-desc           "If you don't have any data yet, try running the workspace first."
   :string/workspace-result-history       "Result History"
   :string/no-results                     "You haven't generated any results yet."
   :string/compare                        "Compare"
   :string/request-to-share-noun          "Data Request"
   :string/request-to-share-dash-title    "Data Requests"
   :string/request-to-share-dash-desc     "Manage data requests to and from other groups and users"
   :string/create-request-to-share        ["Create a new" :string/request-to-share-noun]
   :string/create-request-to-share-desc   "Request a dataset, of a given format, from one or more users or organisations. Ensure the data you receive is valid and track the progress of your request by being able to see who has submit their data and who hasn't."
   :string/get-started                    "Get Started"
   :string/rts-no-requests                ["It looks like you haven't created any" :string/request-to-share-dash-title
                                           "yet. Use the button above to get started!"]
   :string/create-rts-subtitle            "Send a request, to a user or organisation, for data of a specific type"
   :string/create-rts-user                "Recipient Users/Groups"
   :string/create-rts-user-ph             "Search for the users and/or organisations you'd like to send the request to."
   :string/search-results                 "Search Results"
   :string/create-rts-will-be-sent-to     "This request will be sent to:"
   :string/schema                         "Schema"
   :string/create-rts-schema-ph           "Search for the schema you'd like to associate with the uploaded data."
   :string/create-rts-selected-schema     "The selected schema for the requested data:"
   :string/create-rts-destination         "Destination Users/Groups"
   :string/create-rts-destination-ph      "Search for the users and/or organisations you'd like the requested data to be shared with."
   :string/create-rts-will-be-shared-with "The requested data will be shared with:"
   :string/create-rts-recipients-invalid  "Please specify at least one recipient group."
   :string/create-rts-schema-invalid      "Please specify a schema."
   :string/create-rts-destinations-invalid "Please specify at least one destination group."
   :string/message                        "Message"
   :string/create-rts-message-ph          "Add an optional message which the recipients will see when they receive the request."
   :string/create-rts-message-failed      "There was an error creating this Request. Please contact us."
   :string/create-rts-message-created     "Your Request was created successfully! The next step is to email your request to the relevant groups. Click the individual 'Mail' buttons below to do this now:"
   :string/send-mail                      "Send Mail"
   :string/return-to-dash                 "Return to Dashboard"
   :string/rts-404-error                  "Unable to find a Request at this address."
   :string/status                         "Status"
   :string/rts-status-incomplete          "Pending"
   :string/rts-status-complete            "Complete"
   :string/new-data-request-created       ["New" :string/request-to-share-noun "created!"]
   :string/new-data-request-created-desc  ["Congratulations! You've successfully created a new" :string/request-to-share-noun "- please remember to email each of the recipients. This will also give you an opportunity to tailor the message and add any attachments that might help them fulfil the request for data."]
   :string/date                           "Date"
   :string/rts-info-paragraph-1           "Your request for"
   :string/rts-info-paragraph-2           "data, dated"
   :string/rts-info-paragraph-3           "has been responded to by"
   :string/rts-info-paragraph-4           "out of"
   :string/rts-info-paragraph-5           "group(s)."
   :string/rts-info-paragraph-6           "You wrote them the following message:"
   :string/groups                         "Groups"
   :string/outbound-requests              ["Outbound" :string/request-to-share-dash-title]
   :string/rts-email-subject              ["New" :string/request-to-share-noun "from "]
   :string/rts-email-header-line          "To whom it may concern,\n\n"
   :string/rts-email-footer-line          "Here is the link you should use to submit your data:\n\n"
   :string/rts-email-default-body         (str
                                           "You are receiving this email because you belong to the '%s' City Datastore group.\n"
                                           "This is a formal request for submission of '%s' data.\n"
                                           "Please submit the data directly into the City Datastore using the link provided below.\n\n"
                                           "If you feel you've received this requesst in error, please reply directly or liase with other members of the group.")
   :string/from-lower                      "from"
   :string/author                          "Author"
   :string/datastore-name                  "City Data Store"
   :string/data-upload-intro               ["This step-by-step process will guide you through uploading a data set into the" :string/datastore-name "- if you're unsure about any of the steps, please don't hesistate to use our interactive support system and we can offer additional guidance."]
   :string/data-upload-selected-file       "Selected file"
   :string/data-upload-selected-schema     "Selected schema"
   :string/data-upload-step-1              "Select the data you'd like to upload..."
   :string/data-upload-step-2              "Please fill in some information about this data."
   :string/data-upload-step-2-input-1-title "Name of the data"
   :string/data-upload-step-2-input-1-ph    "e.g. 'Housing Data for London'"
   :string/data-upload-step-2-input-2-title "Description of the data"
   :string/data-upload-step-2-input-2-ph    "e.g. 'This data shows housing data across all 33 London boroughs.'"
   :string/data-upload-step-3              "Would you like to share this data with others users or groups?"
   :string/data-upload-step-3-radio-1      "Yes, I'd like to share this data."
   :string/data-upload-step-3-radio-2      "No, this data should be private and only accessible by me."
   :string/data-upload-search-groups       "Search for users and/or groups with whom you'd like to share this data."
   :string/data-upload-step-3-group-heading "This data will be shared with %s user/group(s):"
   :string/remove-lc                        "remove"
   :string/data-upload-step-4               "Confirm"
   :string/data-upload-step-4-decl-unsure   "Please take a moment to check that the steps are filled out correctly and then press the 'Upload' button to begin uploading the data."
   :string/try-again                        "Try Again"
   :string/file-size                        "Size"
   :string/file-uploader                    "Uploader"
   :string/sharing                          "Sharing"
   :string/file-sharing-meta-read           "Meta Read"
   :string/file-sharing-meta-update         "Meta Update"
   :string/file-sharing-file-read           "File Read"
   :string/file-actions-download-file       "Download this file"
   :string/sharing-matrix-group-name        "Group Name"
   :string/sharing-matrix-group-search-ph   "Search for users and/or groups..."} )

(defn resolve-string
  ([r]
   (let [s (get strings r)]
     (if (string? s) s
         (resolve-string s nil))))
  ([r a]
   (cond (keyword? r) (if a (clojure.string/join " " [a (get strings r)]) (get strings r))
         (string? r)  (if a (clojure.string/join " " [a r]) r)
         (vector? r)  (reduce #(resolve-string %2 %1) a r)
         :else nil)))

(defn get-string
  ""
  [keywd & add]
  (if (contains? strings keywd)
    (let [r (resolve-string keywd)]
      (if add
        (str r (first add) (clojure.string/join " " (concat " " (rest add))))
        r))
    (do
      (log/severe "Failed to find string " (str keywd))
      "## ERROR ##")))
